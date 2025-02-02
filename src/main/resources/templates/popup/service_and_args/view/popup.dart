import 'package:arch/side_effect/side_effect_handler.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:widgets/modal_bottom_sheet/modal_bottom_sheet.dart';

import '../controller/{snake_case_name}_controller.dart';
import '../model/{snake_case_name}_args.dart';
import '../model/{snake_case_name}_service.dart';

part '../resources/strings.dart';
part '{snake_case_name}_side_effects_handler.dart';

/// TODO: add description
///
/// Design Link:
/// TODO: add design link
class {name}Popup extends StatefulWidget {

  final {name}Args args;

  const {name}Popup._({
    super.key,
    required this.args,
  });

  static Future<void> show(
    BuildContext context, {
    required {name}Args args,
  }) =>
      ModalBottomSheet.show<void>(
        context: context,
        child: {name}Popup._(args: args),
      );

  @override
  State<{name}Popup> createState() =>
      _{name}PopupState();
}

class _{name}PopupState extends State<{name}Popup> {
  late final {name}Controller controller;

  @override
  void initState() {
    super.initState();

    controller = Get.put(
      {name}Controller(
        service: {name}Service(args: widget.args),
      ),
    );
  }

  @override
  void dispose() {
    Get.delete<{name}Controller>();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) => _{name}SideEffectsHandler(
        child: Placeholder(),
      );
}
