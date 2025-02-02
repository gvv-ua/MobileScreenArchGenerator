import 'package:arch/side_effect/side_effect.dart';
import 'package:equatable/equatable.dart';
import 'package:get/get_state_manager/get_state_manager.dart';

import '../model/{snake_case_name}_args.dart';

part '{snake_case_name}_side_effect.dart';

/// TODO: cover with tests
class {name}Controller extends GetxController
    with WithSideEffects<{name}SideEffect> {
  final {name}Args _args;

  {name}Controller({
    required {name}Args args,
  })  : _args = args;

}
