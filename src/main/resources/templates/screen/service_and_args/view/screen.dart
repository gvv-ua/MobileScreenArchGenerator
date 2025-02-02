import 'package:arch/side_effect/side_effect_handler.dart';
import 'package:concierge/resources/resources.dart';
import 'package:concierge/widgets/app_bars/view/banner_scaffold.dart';
import 'package:concierge/widgets/app_bars/view/custom_app_bar.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../controller/{snake_case_name}_binding.dart';
import '../controller/{snake_case_name}_controller.dart';

part '../resources/strings.dart';
part '{snake_case_name}_side_effects_handler.dart';

/// TODO: add description
///
/// Design Link:
/// TODO: add design link
class {name}Screen extends GetView<{name}Controller> {
  static const routeName = '/{snake_case_name}';

  static final page = GetPage(
    name: {name}Screen.routeName,
    page: {name}Screen.new,
    binding: {name}Binding(),
  );

  const {name}Screen({super.key});

  @override
  Widget build(BuildContext context) => _{name}SideEffectsHandler(
        child: BannerScaffold(
          screenName: routeName,
          appBar: const CustomAppBar(titleText: _Strings.screenTitle),
          body: SafeArea(
            child: Placeholder(),
          ),
        ),
      );
}
