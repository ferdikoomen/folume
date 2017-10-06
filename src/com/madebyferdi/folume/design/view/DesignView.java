package com.madebyferdi.folume.design.view;


import com.madebyferdi.folume.Application;
import com.madebyferdi.folume.controlP5.GuiContainer;
import com.madebyferdi.folume.design.view.controls.*;
import com.madebyferdi.folume.design.view.preview.DesignPreview;
import com.madebyferdi.folume.settings.Style;
import com.madebyferdi.folume.shape.Shape;
import processing.core.PConstants;
import processing.core.PGraphics;


final public class DesignView
{

	// Properties
	private Application app;
	private GuiContainer cp5;
	private PGraphics pg;
	private DesignControlSize controlSize;
	private DesignControlRadius controlRadius;
	private DesignControlRotation controlRotation;
	private DesignControlRibs controlRibs;
	private DesignControlPosition controlPostion;
	private DesignControlButtons controlButtons;
	private DesignControlCurve controlCurve;
	private DesignPreview preview;


	public DesignView(Application app, Shape shape)
	{
		// Store references
		this.app = app;

		// Create GUI layer
		cp5 = new GuiContainer(app);
		cp5.setAutoDraw(false);

		// Create parts
		controlSize = new DesignControlSize(app, cp5, Style.DESIGN_X + 20, Style.DESIGN_Y + 20);
		controlRadius = new DesignControlRadius(app, cp5, Style.DESIGN_X + 280, Style.DESIGN_Y + 20);
		controlRotation = new DesignControlRotation(app, cp5, Style.DESIGN_X + 620, Style.DESIGN_Y + 20);
		controlRibs = new DesignControlRibs(app, cp5, Style.DESIGN_X + 20, Style.DESIGN_Y + 160);
		controlPostion = new DesignControlPosition(app, cp5, Style.DESIGN_X + 440, Style.DESIGN_Y + 160);
		controlButtons = new DesignControlButtons(app, cp5, Style.DESIGN_X + 780, Style.DESIGN_Y + 160);
		controlCurve = new DesignControlCurve(app, shape, cp5, Style.DESIGN_X + 20, Style.DESIGN_Y + 300);
		preview = new DesignPreview(app, shape, cp5, Style.DESIGN_X + 360, Style.DESIGN_Y + 300);

		createBrackground();
	}


	/**
	 * Getters
	 */

	public DesignControlSize getControlSize()
	{
		return controlSize;
	}

	public DesignControlRadius getControlRadius()
	{
		return controlRadius;
	}

	public DesignControlRotation getControlRotation()
	{
		return controlRotation;
	}

	public DesignControlRibs getControlRibs()
	{
		return controlRibs;
	}

	public DesignControlPosition getControlPostion()
	{
		return controlPostion;
	}

	public DesignControlButtons getControlButtons()
	{
		return controlButtons;
	}

	public DesignControlCurve getControlCurve()
	{
		return controlCurve;
	}

	public DesignPreview getPreview()
	{
		return preview;
	}


	/**
	 * Create background layer, note that we only need to do this once
	 * so it's faster to store it in a separate graphics object
	 */
	public void createBrackground()
	{
		// Create new background layer
		pg = app.createGraphics(Style.PRINT_WIDTH, Style.PRINT_HEIGHT, PConstants.P2D);

		// Draw background
		pg.beginDraw();
		pg.background(
			Style.DESIGN_COLOR_BACKGROUND.getRed(),
			Style.DESIGN_COLOR_BACKGROUND.getGreen(),
			Style.DESIGN_COLOR_BACKGROUND.getBlue()
		);

		// Line style
		pg.noFill();
		pg.strokeWeight(1);
		pg.stroke(
			Style.DESIGN_COLOR_FOREGROUND.getRed(),
			Style.DESIGN_COLOR_FOREGROUND.getGreen(),
			Style.DESIGN_COLOR_FOREGROUND.getBlue()
		);

		// Draw horizontal lines
		pg.line(0, 140, Style.DESIGN_WIDTH, 140);
		pg.line(0, 280, Style.DESIGN_WIDTH, 280);

		// Draw vertical lines (row 1)
		pg.line(260, 0, 260, 140);
		pg.line(600, 0, 600, 140);

		// Draw vertical lines (row 2)
		pg.line(420, 140, 420, 280);
		pg.line(760, 140, 760, 280);

		// Draw vertical lines (row 3)
		pg.line(340, 280, 340, Style.DESIGN_HEIGHT);

		pg.endDraw();
	}


	public void draw()
	{
		// Show background
		app.image(pg, Style.DESIGN_X, Style.DESIGN_Y);

		// Update parts
		controlCurve.draw();
		preview.draw();

		// Update GUI elements
		cp5.draw();
	}

}
