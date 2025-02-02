package ua.gvv.mobilescreenarchgenerator.models

enum class TemplateType(val templatePath: String) {
    ScreenWithArgs("screen/args"),
    ScreenWithService("screen/service"),
    ScreenWithServiceAndArgs("screen/service_and_args"),

    PopupWithArgs("popup/args"),
    PopupWithController("popup/controller"),
    PopupWithService("popup/service"),
    PopupWithServiceAndArgs("popup/service_and_args"),
    PopupSimple("popup/simple"),
}