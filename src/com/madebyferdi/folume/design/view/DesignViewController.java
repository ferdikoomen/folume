package com.madebyferdi.folume.design.view;


import com.madebyferdi.folume.Application;
import com.madebyferdi.folume.shape.Shape;

import java.awt.*;


final public class DesignViewController
{

	// Properties
	private Application app;
	private Shape shape;
	private DesignView view;
	private boolean invalidated = false;


	public DesignViewController(Application app, Shape shape, DesignView view)
	{
		// Store references
		this.app = app;
		this.view = view;
		this.shape = shape;

		// Update design when input changes
		view.getControlSize().getHeightKnob().onChange(this::invalidate);
		view.getControlSize().getStepsKnob().onChange(this::invalidate);
		view.getControlSize().getDegreesKnob().onChange(this::invalidate);
		view.getControlRadius().getRadiusInsideTopKnob().onChange(this::invalidate);
		view.getControlRadius().getRadiusInsideBottomKnob().onChange(this::invalidate);
		view.getControlRadius().getRadiusOutsideTopKnob().onChange(this::invalidate);
		view.getControlRadius().getRadiusOutsideBottomKnob().onChange(this::invalidate);
		view.getControlRotation().getRotateInsideTopKnob().onChange(this::invalidate);
		view.getControlRotation().getRotateInsideBottomKnob().onChange(this::invalidate);
		view.getControlRotation().getRotateOutsideTopKnob().onChange(this::invalidate);
		view.getControlRotation().getRotateOutsideBottomKnob().onChange(this::invalidate);
		view.getControlRibs().getRibCountKnob().onChange(this::invalidate);
		view.getControlRibs().getRibWidthInsideTopKnob().onChange(this::invalidate);
		view.getControlRibs().getRibWidthInsideBottomKnob().onChange(this::invalidate);
		view.getControlRibs().getRibWidthOutsideTopKnob().onChange(this::invalidate);
		view.getControlRibs().getRibWidthOutsideBottomKnob().onChange(this::invalidate);
		view.getControlPostion().getXTopKnob().onChange(this::invalidate);
		view.getControlPostion().getYTopKnob().onChange(this::invalidate);
		view.getControlPostion().getXBottomKnob().onChange(this::invalidate);
		view.getControlPostion().getYBottomKnob().onChange(this::invalidate);
		view.getControlCurve().onChange(this::invalidate);

		// Reset
		view.getControlButtons().getResetButton().onClick(this::reset);

		updateDesign();
	}


	/**
	 * Set the invalidation flag, this will update
	 * the design on the next draw call
	 */
	private void invalidate()
	{
		invalidated = true;
	}


	/**
	 * Reset controls and input
	 */
	public void reset()
	{
		view.getControlSize().reset();
		view.getControlRadius().reset();
		view.getControlRotation().reset();
		view.getControlRibs().reset();
		view.getControlPostion().reset();
		view.getControlCurve().reset();
		view.getPreview().reset();
		updateDesign();
	}


	/**
	 * Update the design
	 */
	private void updateDesign()
	{
		// Get values
		double height = view.getControlSize().getHeightKnob().getDouble();
		double steps = view.getControlSize().getStepsKnob().getDouble();
		double degrees = view.getControlSize().getDegreesKnob().getDouble();
		double radiusInsideTop = view.getControlRadius().getRadiusInsideTopKnob().getDouble();
		double radiusInsideBottom = view.getControlRadius().getRadiusInsideBottomKnob().getDouble();
		double radiusOutsideTop = view.getControlRadius().getRadiusOutsideTopKnob().getDouble();
		double radiusOutsideBottom = view.getControlRadius().getRadiusOutsideBottomKnob().getDouble();
		double rotateInsideTop = view.getControlRotation().getRotateInsideTopKnob().getDouble();
		double rotateInsideBottom = view.getControlRotation().getRotateInsideBottomKnob().getDouble();
		double rotateOutsideTop = view.getControlRotation().getRotateOutsideTopKnob().getDouble();
		double rotateOutsideBottom = view.getControlRotation().getRotateOutsideBottomKnob().getDouble();
		double ribCount = view.getControlRibs().getRibCountKnob().getDouble();
		double ribWidthInsideTop = view.getControlRibs().getRibWidthInsideTopKnob().getDouble();
		double ribWidthInsideBottom = view.getControlRibs().getRibWidthInsideBottomKnob().getDouble();
		double ribWidthOutsideTop = view.getControlRibs().getRibWidthOutsideTopKnob().getDouble();
		double ribWidthOutsideBottom = view.getControlRibs().getRibWidthOutsideBottomKnob().getDouble();
		double xTop = view.getControlPostion().getXTopKnob().getDouble();
		double yTop = view.getControlPostion().getYTopKnob().getDouble();
		double xBottom = view.getControlPostion().getXBottomKnob().getDouble();
		double yBottom = view.getControlPostion().getYBottomKnob().getDouble();

		// We calculate the shape outside of the main thread!
		EventQueue.invokeLater(() -> {

			// Update curves
			view.getControlCurve().update(
				height,
				radiusInsideTop, radiusInsideBottom,
				radiusOutsideTop, radiusOutsideBottom
			);

			// Draw design
			shape.generateDesign(
				height,
				steps,
				degrees,
				xTop, yTop,
				xBottom, yBottom,
				radiusInsideTop, radiusInsideBottom,
				radiusOutsideTop, radiusOutsideBottom,
				ribCount,
				ribWidthInsideTop, ribWidthInsideBottom,
				ribWidthOutsideTop, ribWidthOutsideBottom,
				rotateInsideTop, rotateInsideBottom,
				rotateOutsideTop, rotateOutsideBottom
			);

			// Update the preview
			view.getPreview().invalidate();
		});
	}


	public void update()
	{
		// Draw the view
		view.draw();

		// After our drawing action we check if the shape needs to be updated.
		// We do this after the draw, since from this point on the separate thread
		// can mess with the data. If we do this before draw we have a larger change
		// to run into deadlocks.
		if (invalidated) {
			invalidated = false;
			updateDesign();
		}
	}
}
