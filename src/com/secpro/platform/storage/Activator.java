package com.secpro.platform.storage;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.secpro.platform.core.services.ServiceHelper;
import com.secpro.platform.log.utils.PlatformLogger;
import com.secpro.platform.storage.services.DataBaseStorageService;

public class Activator implements BundleActivator {
	// Logging Object
	final private static PlatformLogger theLogger = PlatformLogger.getLogger(Activator.class);

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		ServiceHelper.registerService(new DataBaseStorageService());
		theLogger.info("#PL-STORAGE is started~");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
