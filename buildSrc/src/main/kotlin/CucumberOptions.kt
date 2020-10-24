import org.gradle.api.file.Directory
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputDirectory
import org.gradle.process.CommandLineArgumentProvider

open class CucumberOptions(@OutputDirectory val outputs: Provider<Directory>) : CommandLineArgumentProvider {

    @Internal
    val htmlReport = outputs.map { it.file("cucumber.html") }

    @Internal
    val junitReport = outputs.map { it.file("cucumber.xml") }

    @Internal
    val jsonReport = outputs.map { it.file("cucumber.json") }

    override fun asArguments() =
        listOf("-Dcucumber.plugin=pretty,html:${htmlReport.get().asFile},junit:${junitReport.get().asFile},json:${jsonReport.get().asFile}")
}
