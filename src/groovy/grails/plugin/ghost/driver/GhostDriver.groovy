package grails.plugin.ghost.driver

import groovy.lang.Lazy

import org.openqa.selenium.Capabilities
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.remote.DesiredCapabilities
import org.apache.commons.lang3.SystemUtils
import org.codehaus.groovy.grails.plugins.GrailsPluginUtils

class GhostDriver {

	@Lazy static driver = create()
	
	static create(Capabilities capabilities = null) {

		def pluginDir = GrailsPluginUtils.getPluginDirForName('ghost-driver')?.file?.parentFile?.absolutePath
		pluginDir = pluginDir ? pluginDir + '/' : ''

		if (!capabilities) capabilities = new DesiredCapabilities()

		def bin = SystemUtils.IS_OS_WINDOWS ? 'windows/phantomjs.exe' : 
		SystemUtils.IS_OS_MAC_OSX ? 'macosx/phantomjs' : 
		SystemUtils.IS_OS_LINUX ? (SystemUtils.OS_ARCH.contains('64') ? 'linux64/phantomjs' : 'linux/phantomjs') : null
		if (!bin) throw new Exception("Unsupported OS")
		
		capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "${pluginDir}lib/phantomjs-1.9.7/$bin")

		new PhantomJSDriver(capabilities)

	}

}
