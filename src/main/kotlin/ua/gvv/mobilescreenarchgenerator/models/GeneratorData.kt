package ua.gvv.mobilescreenarchgenerator.models

data class GeneratorData(
    val screenName: String,
    val isPopup: Boolean,
    val shouldAddController: Boolean,
    val shouldAddArgs: Boolean,
    val shouldAddService: Boolean
) {

    val screenNameTruncated: String
        get() = screenName.replace(Regex("Screen$"), "")
            .replace(Regex("Popup$"), "")

    val screenNameSnakeCase: String
        get() = camelToSnake(screenNameTruncated)

    val screenNameReadable: String
        get() = camelToReadable(screenNameTruncated)

    val templateType: TemplateType
        get() {
            return when {
                isPopup && shouldAddArgs && shouldAddService -> TemplateType.PopupWithServiceAndArgs
                isPopup && shouldAddArgs -> TemplateType.PopupWithArgs
                isPopup && shouldAddService -> TemplateType.PopupWithService
                isPopup && shouldAddController -> TemplateType.PopupWithController
                isPopup -> TemplateType.PopupSimple
                shouldAddArgs && shouldAddService -> TemplateType.ScreenWithServiceAndArgs
                shouldAddArgs -> TemplateType.ScreenWithArgs
                shouldAddService -> TemplateType.ScreenWithService
                else -> TemplateType.ScreenWithArgs
            }
        }

    private fun camelToSnake(input: String): String {
        return input.replace(Regex("([a-z0-9])([A-Z])"), "$1_$2")
            .replace(Regex("([A-Z])([A-Z][a-z])"), "$1_$2")
            .lowercase()
    }

    private fun camelToReadable(input: String): String {
        return input.replace(Regex("([a-z0-9])([A-Z])"), "$1 $2")
            .replace(Regex("([A-Z])([A-Z][a-z])"), "$1 $2")
    }

}
