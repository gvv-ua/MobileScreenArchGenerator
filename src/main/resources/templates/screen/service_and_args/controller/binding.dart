import 'package:get/get.dart';

import '../model/{snake_case_name}_service.dart';
import '{snake_case_name}_controller.dart';

class {name}Binding extends Bindings {
  @override
  void dependencies() {
    Get.put(
      {name}Controller(
        service: {name}Service(args: Get.arguments),
      ),
    );
  }
}