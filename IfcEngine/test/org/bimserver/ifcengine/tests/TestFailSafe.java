package org.bimserver.ifcengine.tests;

import java.io.File;
import java.util.List;

import org.bimserver.ifcengine.JvmIfcEngine;
import org.bimserver.plugins.renderengine.RenderEngine;
import org.bimserver.plugins.renderengine.RenderEngineException;
import org.bimserver.plugins.renderengine.RenderEngineGeometry;
import org.bimserver.plugins.renderengine.RenderEngineInstance;
import org.bimserver.plugins.renderengine.RenderEngineModel;
import org.bimserver.plugins.renderengine.RenderEngineSettings;

public class TestFailSafe {
	public static void main(String[] args) {
		new TestFailSafe().start();
	}

	private void start() {
		try {
			RenderEngine failSafeIfcEngine = new JvmIfcEngine(new File("../buildingSMARTLibrary/schema/IFC2X3_TC1.exp"), new File("lib/64"), new File("tmp"), System.getProperty("java.class.path"));
			failSafeIfcEngine.init();
			RenderEngineModel model = failSafeIfcEngine.openModel(new File("../TestData/data/AC11-Institute-Var-2-IFC.ifc"));
			model.setSettings(new RenderEngineSettings());
			RenderEngineGeometry geometry = model.finalizeModelling(model.initializeModelling());

			List<? extends RenderEngineInstance> instances = model.getInstances("IFCWINDOW");
			for (RenderEngineInstance instance : instances) {
				int index = geometry.getIndex(instance.getVisualisationProperties().getStartIndex());
				System.out.println(geometry.getVertex(index * 3));
			}
			
			model.close();
			failSafeIfcEngine.close();
		} catch (RenderEngineException e) {
			e.printStackTrace();
		}
	}
}
