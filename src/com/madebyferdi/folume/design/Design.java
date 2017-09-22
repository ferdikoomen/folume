package com.madebyferdi.folume.design;


import com.madebyferdi.folume.Application;
import com.madebyferdi.folume.design.view.DesignView;
import com.madebyferdi.folume.design.view.DesignViewController;
import com.madebyferdi.folume.shape.Shape;
import processing.data.JSONObject;

import java.io.File;


final public class Design
{

	// Properties
	private Application app;
	private Shape shape;
	private DesignView view;
	private DesignViewController viewController;


	public Design(Application app, Shape shape)
	{
		// Store references
		this.app = app;
		this.shape = shape;

		// Create view
		view = new DesignView(app, shape);
		viewController = new DesignViewController(app, shape, view);

		// Load and save settings
		view.getControlButtons().getLoadButton().onClick(() -> {
			app.selectInput("Open", "loadSettingsFile", new File("design.json"), this);
		});
		view.getControlButtons().getSaveButton().onClick(() -> {
			app.selectOutput("Save", "saveSettingsFile", new File("design.json"), this);
		});
		view.getControlButtons().getSvgButton().onClick(() -> {
			app.selectInput("Open", "loadSvgFile", new File("curve.svg"), this);
		});
	}


	/**
	 * Getters
	 */
	public DesignViewController getViewController()
	{
		return viewController;
	}

	public DesignView getView()
	{
		return view;
	}


	/**
	 * Load settings file
	 *
	 * @param file: JSON file reference
	 */
	public void loadSettingsFile(File file)
	{
		if (file != null) {
			try {
				JSONObject json = app.loadJSONObject(file.getAbsolutePath());
				view.getControlSize().getHeightKnob().setValue(json.getDouble("height"));
				view.getControlSize().getStepsKnob().setValue(json.getDouble("steps"));
				view.getControlSize().getDegreesKnob().setValue(json.getDouble("degrees"));
				view.getControlRadius().getRadiusInsideTopKnob().setValue(json.getDouble("radiusInsideTop"));
				view.getControlRadius().getRadiusInsideBottomKnob().setValue(json.getDouble("radiusInsideBottom"));
				view.getControlRadius().getRadiusOutsideTopKnob().setValue(json.getDouble("radiusOutsideTop"));
				view.getControlRadius().getRadiusOutsideBottomKnob().setValue(json.getDouble("radiusOutsideBottom"));
				view.getControlRotation().getRotateInsideTopKnob().setValue(json.getDouble("rotateInsideTop"));
				view.getControlRotation().getRotateInsideBottomKnob().setValue(json.getDouble("rotateInsideBottom"));
				view.getControlRotation().getRotateOutsideTopKnob().setValue(json.getDouble("rotateOutsideTop"));
				view.getControlRotation().getRotateOutsideBottomKnob().setValue(json.getDouble("rotateOutsideBottom"));
				view.getControlRibs().getRibCountKnob().setValue(json.getDouble("ribCount"));
				view.getControlRibs().getRibWidthInsideTopKnob().setValue(json.getDouble("ribWidthInsideTop"));
				view.getControlRibs().getRibWidthInsideBottomKnob().setValue(json.getDouble("ribWidthInsideBottom"));
				view.getControlRibs().getRibWidthOutsideTopKnob().setValue(json.getDouble("ribWidthOutsideTop"));
				view.getControlRibs().getRibWidthOutsideBottomKnob().setValue(json.getDouble("ribWidthOutsideBottom"));
				view.getControlCurve().setOffsetInsideC1X(json.getDouble("curveInsideC1X"));
				view.getControlCurve().setOffsetInsideC1Y(json.getDouble("curveInsideC1Y"));
				view.getControlCurve().setOffsetInsideC2X(json.getDouble("curveInsideC2X"));
				view.getControlCurve().setOffsetInsideC2Y(json.getDouble("curveInsideC2Y"));
				view.getControlCurve().setOffsetOutsideC1X(json.getDouble("curveOutsideC1X"));
				view.getControlCurve().setOffsetOutsideC1Y(json.getDouble("curveOutsideC1Y"));
				view.getControlCurve().setOffsetOutsideC2X(json.getDouble("curveOutsideC2X"));
				view.getControlCurve().setOffsetOutsideC2Y(json.getDouble("curveOutsideC2Y"));
			} catch (Exception e) {
				// Ignore
			}
		}
	}


	/**
	 * Save settings file
	 *
	 * @param file: JSON file reference
	 */
	public void saveSettingsFile(File file)
	{
		if (file != null) {
			JSONObject json = new JSONObject();
			json.setDouble("height", view.getControlSize().getHeightKnob().getDouble());
			json.setDouble("steps", view.getControlSize().getStepsKnob().getDouble());
			json.setDouble("degrees", view.getControlSize().getDegreesKnob().getDouble());
			json.setDouble("radiusInsideTop", view.getControlRadius().getRadiusInsideTopKnob().getDouble());
			json.setDouble("radiusInsideBottom", view.getControlRadius().getRadiusInsideBottomKnob().getDouble());
			json.setDouble("radiusOutsideTop", view.getControlRadius().getRadiusOutsideTopKnob().getDouble());
			json.setDouble("radiusOutsideBottom", view.getControlRadius().getRadiusOutsideBottomKnob().getDouble());
			json.setDouble("rotateInsideTop", view.getControlRotation().getRotateInsideTopKnob().getDouble());
			json.setDouble("rotateInsideBottom", view.getControlRotation().getRotateInsideBottomKnob().getDouble());
			json.setDouble("rotateOutsideTop", view.getControlRotation().getRotateOutsideTopKnob().getDouble());
			json.setDouble("rotateOutsideBottom", view.getControlRotation().getRotateOutsideBottomKnob().getDouble());
			json.setDouble("ribCount", view.getControlRibs().getRibCountKnob().getDouble());
			json.setDouble("ribWidthInsideTop", view.getControlRibs().getRibWidthInsideTopKnob().getDouble());
			json.setDouble("ribWidthInsideBottom", view.getControlRibs().getRibWidthInsideBottomKnob().getDouble());
			json.setDouble("ribWidthOutsideTop", view.getControlRibs().getRibWidthOutsideTopKnob().getDouble());
			json.setDouble("ribWidthOutsideBottom", view.getControlRibs().getRibWidthOutsideBottomKnob().getDouble());
			json.setDouble("curveInsideC1X", view.getControlCurve().getOffsetInsideC1X());
			json.setDouble("curveInsideC1Y", view.getControlCurve().getOffsetInsideC1Y());
			json.setDouble("curveInsideC2X", view.getControlCurve().getOffsetInsideC2X());
			json.setDouble("curveInsideC2Y", view.getControlCurve().getOffsetInsideC2Y());
			json.setDouble("curveOutsideC1X", view.getControlCurve().getOffsetOutsideC1X());
			json.setDouble("curveOutsideC1Y", view.getControlCurve().getOffsetOutsideC1Y());
			json.setDouble("curveOutsideC2X", view.getControlCurve().getOffsetOutsideC2X());
			json.setDouble("curveOutsideC2Y", view.getControlCurve().getOffsetOutsideC2Y());
			app.saveJSONObject(json, file.getAbsolutePath());
		}
	}


	/**
	 * Select file callback. Note that this method runs in a different thread
	 * therefore we are not allowed to do any draw methods...
	 *
	 * @param file: Selected file
	 */
	public void loadSvgFile(File file)
	{
		if (file != null && file.isFile() && file.canRead()) {
			shape.getSvg().load(file.getAbsolutePath());
		}
	}


	/**
	 * Update loop
	 */
	public void update()
	{
		viewController.update();
	}

}
