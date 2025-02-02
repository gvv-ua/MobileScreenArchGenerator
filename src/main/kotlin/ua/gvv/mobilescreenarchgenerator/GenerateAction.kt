package ua.gvv.mobilescreenarchgenerator

import com.esotericsoftware.kryo.kryo5.minlog.Log
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.writeText
import java.net.URLDecoder
import ua.gvv.mobilescreenarchgenerator.models.GeneratorData
import java.util.jar.JarFile
import ua.gvv.mobilescreenarchgenerator.models.TemplateType

class GenerateAction : AnAction() {
    /**
     * Regex to extract the last directory name from a file path
     * Example: /path/to/dir/file.dart -> dir
     */
    private val dirRegex = Regex(".*/([^/]+)/[^/]+$")

    /**
     * Regex to extract the file name from a file path
     * Example: /path/to/dir/file.dart -> file.dart
     */
    private val fileRegex = Regex(".*/([^/]+)$")

    override fun actionPerformed(event: AnActionEvent) {
        val dialog = GenerateDialog()
        dialog.show()
        if (dialog.isOK) {
            val selectedDir = getSelectedDirectory(event) ?: return

            val project = event.project ?: return

            WriteCommandAction.runWriteCommandAction(project) {
                generateStructure(dialog.getData(), selectedDir)
            }
        }
    }

    /**
     * Generate the entire screen structure with all the necessary dirs and files
     */
    private fun generateStructure(data: GeneratorData, selectedDir: VirtualFile) {
        // Create the screen directory
        val screenPath = "${selectedDir.path}/${data.screenNameSnakeCase}"
        val screenDir = VfsUtil.createDirectories(screenPath)

        // Get all template files for the selected template type
        val files = getTemplateFiles(data.templateType)

        // Create all the necessary files in the screen directory
        files.forEach {
            createDartFile(data, screenDir, it)
        }
    }

    /**
     * Get all template files for the given template type
     */
    private fun getTemplateFiles(templateType: TemplateType): List<String> {
        val classLoader = this.javaClass.classLoader
        val url = classLoader.getResource("$TEMPLATES/${templateType.templatePath}")
        if (url != null && url.protocol == "jar") {
            val jarPath = url.path.substringBefore("!").removePrefix("file:")
            val resourcePath = "${TEMPLATES}/${templateType.templatePath}/"

            // Get all files in the jar that match the resource path
            val files = getFilesInJar(jarPath, resourcePath)

            // Filter out the README.md file
            return files.filter { !it.endsWith(READ_ME_FILE_NAME) }
        }
        return emptyList()
    }

    /**
     * Return all files in a jar that match the given resource path
     */
    private fun getFilesInJar(jarPath: String, resourcePath: String): List<String> {
        val decodedPath = URLDecoder.decode(jarPath, "UTF-8")
        val jarFile = JarFile(decodedPath)
        return jarFile.entries().asSequence()
            .map { it.name }
            .filter { it.startsWith(resourcePath) && !it.endsWith("/") } // Get only files, not dirs
            .toList()
    }

    /**
     * Create a dart file from a template
     */
    private fun createDartFile(data: GeneratorData, parentDir: VirtualFile, file: String) {
        // Extract the directory name from the file path
        val match = dirRegex.find(file)
        val dirName = match!!.groupValues[1]

        // Create the directory if it doesn't exist
        val dir = VfsUtil.createDirectories("${parentDir.path}/$dirName")

        // Read the template file and replace the parameters with the actual values
        val classLoader = this.javaClass.classLoader
        val template = classLoader.getResource(file)
        val content = template?.readText()?.replaceParams(data)

        // Extract the file name from the file path
        val fileName = fileRegex.find(file)!!.groupValues[1]

        // Create the file in the directory
        val finalFileName = if (fileName != "strings.dart") "${data.screenNameSnakeCase}_$fileName" else fileName

        // Write the content to the file
        dir.createChildData(this, finalFileName).writeText(content ?: "")
    }

    /**
     * Get the selected directory in the project view
     * The directory where the new screen will be generated
     */
    private fun getSelectedDirectory(event: AnActionEvent): VirtualFile? {
        val selectedFiles = event.getData(com.intellij.openapi.actionSystem.CommonDataKeys.VIRTUAL_FILE_ARRAY)
        return selectedFiles?.firstOrNull()
    }

    companion object {
        // Path to the templates directory
        const val TEMPLATES = "templates"

        // Name of the README.md file
        const val READ_ME_FILE_NAME = "README.md"

        private val LOG = Logger.getInstance(GenerateAction::class.java)
    }
}

/**
 * Replace the template parameters with the actual values
 */
fun String.replaceParams(data: GeneratorData): String {
    var result = this
    val params = mapOf(
        "name" to data.screenNameTruncated,
        "snake_case_name" to data.screenNameSnakeCase,
        "readable_name" to data.screenNameReadable
    )
    params.forEach { (key, value) ->
        result = result.replace("{$key}", value)
    }
    return result
}