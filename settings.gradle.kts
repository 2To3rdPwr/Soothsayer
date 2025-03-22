pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Soothsayer"
include(":app")
include(":screen:CharacterSheet:public")
include(":screen:CharacterSheet:impl")
include(":screen:InventoryScreen:public")
include(":screen:InventoryScreen:impl")
include(":library:CommonUiResources")
include(":screen:Shop:public")
include(":screen:Shop:impl")
include(":library:Navigation:public")
include(":library:Navigation:impl")
include(":library:Snackbar:public")
include(":library:Snackbar:impl")
include(":data:CharacterData:public")
include(":data:CharacterData:impl")
include(":data:InventoryData:public")
include(":data:InventoryData:impl")
include(":screen:CharacterSelect:public")
include(":screen:CharacterSelect:impl")
