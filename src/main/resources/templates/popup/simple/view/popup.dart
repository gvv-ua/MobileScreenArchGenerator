import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:widgets/modal_bottom_sheet/modal_bottom_sheet.dart';

part '../resources/strings.dart';

/// TODO: add description
///
/// Design Link:
/// TODO: add design link
class {name}Popup extends StatelessWidget {

  const {name}Popup._();

  static Future<void> show(
    BuildContext context) =>
      ModalBottomSheet.show<void>(
        context: context,
        child: {name}Popup._(),
      );

  @override
  Widget build(BuildContext context) => Placeholder();
}
