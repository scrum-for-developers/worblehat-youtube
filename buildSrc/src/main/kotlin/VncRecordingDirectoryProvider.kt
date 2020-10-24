import org.gradle.api.file.Directory
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.OutputDirectory
import org.gradle.process.CommandLineArgumentProvider

open class VncRecordingDirectoryProvider(@OutputDirectory val outputDirectory: Provider<Directory>) : CommandLineArgumentProvider {

    override fun asArguments() = listOf("-Dtestcontainers.vnc.recordingDirectory=${outputDirectory.get().asFile}")
}
