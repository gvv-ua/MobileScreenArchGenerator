package ua.gvv.mobilescreenarchgenerator

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import java.awt.FlowLayout
import java.awt.Dimension
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JCheckBox
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JSeparator
import javax.swing.JTextField
import javax.swing.SwingConstants
import ua.gvv.mobilescreenarchgenerator.models.GeneratorData

class GenerateDialog : DialogWrapper(true) {

    private var screenName: String = ""

    private var isPopup: Boolean = false
    private var shouldAddController: Boolean = true
    private var shouldAddArgs: Boolean = true
    private var shouldAddService: Boolean = true

    private lateinit var screenNameTextField: JTextField
    private lateinit var controllerCheckBox: JCheckBox

    init {
        title = "Generate Screen"
        isResizable = false
        init()
    }

    fun getData(): GeneratorData {
        return GeneratorData(
            screenName = screenName,
            isPopup = isPopup,
            shouldAddController = shouldAddController,
            shouldAddArgs = shouldAddArgs,
            shouldAddService = shouldAddService
        )
    }

    override fun createCenterPanel(): JComponent {
        return JPanel().apply {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
            alignmentX = JComponent.LEFT_ALIGNMENT

            add(buildTextField().apply {
                alignmentX = JComponent.LEFT_ALIGNMENT
                maximumSize = Dimension(300, preferredSize.height) // Prevent stretching
            })

            add(JCheckBox("Is pop-up?").apply {
                isSelected = isPopup
                addItemListener {
                    isPopup = isSelected
                    if (!isPopup) {
                        controllerCheckBox.isSelected = true
                    }
                    controllerCheckBox.isEnabled = isPopup
                }
            })

            add(Box.createVerticalStrut(10)) // Space

            add(JSeparator(SwingConstants.HORIZONTAL).apply {
                preferredSize = Dimension(200, 10)
            })

            add(JCheckBox("Generate controller").apply {
                alignmentX = JComponent.LEFT_ALIGNMENT
                controllerCheckBox = this
                isEnabled = isPopup
                isSelected = shouldAddController
                addItemListener {
                    shouldAddController = isSelected
                }
            })

            add(Box.createVerticalStrut(10)) // Space

            add(JCheckBox("Generate args").apply {
                isSelected = shouldAddArgs
                addItemListener {
                    shouldAddArgs = isSelected
                }
            })

            add(Box.createVerticalStrut(10)) // Space

            add(JCheckBox("Generate service").apply {
                isSelected = shouldAddService
                addItemListener {
                    shouldAddService = isSelected
                }
            })
        }
    }

    private fun buildTextField(): JPanel {
        return JPanel(FlowLayout(FlowLayout.LEFT)).apply {
            add(JLabel("Screen name: "))
            add(JTextField().apply {
                screenNameTextField = this
                preferredSize = Dimension(200, 30)
                document.addDocumentListener(object : javax.swing.event.DocumentListener {
                    override fun insertUpdate(e: javax.swing.event.DocumentEvent?) {
                        screenName = text
                    }

                    override fun removeUpdate(e: javax.swing.event.DocumentEvent?) {
                        screenName = text
                    }

                    override fun changedUpdate(event: javax.swing.event.DocumentEvent?) {
                        screenName = text
                    }
                })
            })
        }
    }

    override fun doOKAction() {
        val validationInfo = doValidate()
        if (validationInfo == null) {
            super.close(OK_EXIT_CODE)
        }
    }

    override fun doValidate(): ValidationInfo? {
        if (screenName.trim().isEmpty()) {
            return ValidationInfo("Feature name cannot be empty")
        }
        return null
    }

    override fun getPreferredFocusedComponent(): JComponent {
        return screenNameTextField
    }
}