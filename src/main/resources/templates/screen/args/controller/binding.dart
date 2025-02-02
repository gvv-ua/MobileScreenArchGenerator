import 'package:get/get.dart';

import '{snake_case_name}_controller.dart';

class {name}Binding extends Bindings {
  @override
  void dependencies() {
    Get.put(
      {name}Controller(
        args: Get.arguments,
      ),
    );
  }
}