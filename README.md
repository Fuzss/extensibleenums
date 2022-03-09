# Extensible Enums

A Minecraft mod. Downloads can be found on CurseForge.

![](https://i.imgur.com/Mlxi8cy.png)

## ABOUT THE PROJECT
This is a teeny tiny library for extending enums using the `Unsafe` class.

The main use case for this is extending the `EnchantmentType`/`EnchantmentCategory` enum which additionally to being an enum is also an anonymous class, making extending even more difficult.
Now it's just a single line and you already have your very own `EnchantmentType`/`EnchantmentCategory`:
```java
public static final EnchantmentTarget WATERING_CAN = ExtensibleEnchantmentTarget.create("WATERING_CAN", item -> item instanceof WateringCan);
```

## WARNING
First of all be aware this library is just **one big hack** intended as a last resort when you've run out of options (such as the `EnchantmentType`/`EnchantmentCategory` case illustrated above).
Due to all the restrictions in newer Java versions (the module system and restrictions on reflection) the only way something like this seems to be possible is using the `Unsafe` class and hacking our way into an enum by directly manipulating memory addresses.
**So think thoroughly if extending an enum is really your only option!**

Also make sure to never extend an enum that might be used in a **switch expression** anywhere (even if that's not the case in vanilla, another mod might use it there), since extending that enum will break most switch expressions (switch statements are affected too, but do not break as easily).

## DEVELOPER INFORMATION

### Adding to your workspace
There are no precompiled jars published for this project, therefore you'll have to clone this repository yourself and publish everything to Maven Local. This is done by running the `publishMavenJavaPublicationToMavenLocal` task in Gradle (found in the `publishing` group).

Then in your `build.gradle` file make sure you include the following lines to add the library. They also make sure when compiling your mod **Extensible Enums** is included as a nested jar (due to `include`), so you do not have to depend on any external dependencies. An example for replacing `${modVersion}` would be `v3.0.0-1.18.2-Fabric`.
```groovy
repositories {
    mavenLocal()
}

dependencies {
    modImplementation include("fuzs.extensibleenums:ExtensibleEnums:${modVersion}")
}
```

### Adding to your mod
Then finally in your `fabric.mod.json` add **Extensible Enums** as a hard-dependency by including it in the `depends` block. This is important, as otherwise your mod could be loaded before **Extensible Enums** is.
```json
  "depends": {
    "extensibleenums": "*"
  }
```

### Working with the library
**Relevant classes are found in the `fuzs.extensibleenums.core` package, nothing outside should be touched!**

#### Adding an enum value
Simply create your new enum value by calling `UnsafeExtensibleEnum::invokeEnumConstructor`. Your new enum value will automatically be added to the internal enum array and will also be given the next available ordinal. Make sure the name you choose does not exist in the enum yet, otherwise an exception will be thrown.
A few overloads exist for this method which you should probably use, since some parameters are not required most of the time:
- `Class<? extends T> enumConcreteClass` is only required when `enumMainClass` is an abstract class, you then need to supply the class of any anonymous implementation taken from any enum value (by calling `Enum::getClass`).
- `int internalId` is used for manually setting your new enum value's ordinal and will prevent it from being added to the internal enum array. Overloads set this value to `-1` allowing an ordinal to be assigned automatically and the new value to be added to the enum value array.

```java
    /**
     * create a new enum constant, set <code>name</code> and <code>ordinal</code> fields accordingly (since we invoke the constant using unsafe, no fields will be initialized)
     * and also add it to the enum values array, clearing enum cache (used for switch statements) afterwards
     * @param enumMainClass the enum class to add a constant to
     * @param enumConcreteClass the enum class to create the constant from, should usually be the same as <code>enumMainClass</code>, but in case of an abstract enum supply the class of any of its constants
     * @param internalName name of the new enum value
     * @param internalId ordinal id, controls if the new enum constant is properly added to enum values, set this as -1 to do so, specify a different value to skip it
     * @param <T> enum type
     * @return the new enum constant
     *
     * @throws Throwable something went wrong during unsafe operations oh no
     */
    public static <T extends Enum<T>> T invokeEnumConstructor(Class<T> enumMainClass, Class<? extends T> enumConcreteClass, String internalName, int internalId) throws Throwable
```

#### Adding an `EnchantmentCategory`/`EnchantmentType` value
Simply create your new enum value by calling `ExtensibleEnchantmentCategory::create` and supplying a name and a `Predicate<Item>`. The new value will not be created directly from `EnchantmentCategory`/`EnchantmentType` since the class is abstract, but instead one of the anonymous classes created by defining enum values is used.
The predicate is then added via a mixin which overrides the functionality of `EnchantmentCategory::canEnchant`/`EnchantmentType::isAcceptableItem` if a predicate is present.

```java
    /**
     * create a new {@link net.minecraft.world.item.enchantment.EnchantmentCategory} enum constant
     * @param internalName name of enum constant
     * @param canApplyTo which item this type can be applied to
     * @return new enum constant
     */
    static EnchantmentCategory create(String internalName, Predicate<Item> canApplyTo)
```

#### Adding a value to another anonymous enum class
As with `EnchantmentCategory`/`EnchantmentType` you'll also have to use a custom mixin to add relevant functionality to an anonymous class defined by one of the enum values. Check the implementation descrived above, it should be relatively straight forward to adopt.

If you have any suggestion for more enum values that should be included in the library itself feel free to contact me with your suggestion or make a pull request.