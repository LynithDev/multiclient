plugins {

}

rootProject.name = "client"

var versions = arrayOf(
    "1.8.9",
    "1.20.2"
)

include(":Core", ":JavaAgent")
versions.forEach { version ->
    include(":Versions:${version}")
}

include(":plugins:TestPlugin")
