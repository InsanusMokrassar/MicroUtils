job("Build and run tests") {
    container(displayName = "Run gradle build", image = "openjdk:11") {
        kotlinScript { api ->
            // here can be your complex logic
            api.gradlew("build")
        }
    }
}
