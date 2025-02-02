part of '{snake_case_name}_screen.dart';

class _{name}SideEffectsHandler extends SideEffectHandler<
    {name}Controller, {name}SideEffect> {
  _{name}SideEffectsHandler({super.child})
      : super(
          onSideEffect: (context, sideEffect) {
            switch (sideEffect as {name}SideEffect) {
            }
          },
        );
}
