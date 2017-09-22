package com.madebyferdi.folume.utils;


import com.madebyferdi.folume.Application;
import processing.core.PConstants;
import processing.core.PGraphics;

final public class Camera
{

	// Properties
	private Application app;
	private PGraphics pg;
	private double viewportX;
	private double viewportY;
	private double viewportWidth;
	private double viewportHeight;
	private double cameraAlphaDefault = 0;
	private double cameraBetaDefault = 0;
	private double cameraRadiusDefault = 0;
	private double centerXDefault = 0;
	private double centerYDefault = 0;
	private double centerZDefault = 0;
	private double cameraAlpha = 0;
	private double cameraBeta = 0;
	private double cameraRadius = 0;
	private double cameraX = 0;
	private double cameraY = 0;
	private double cameraZ = 0;
	private double centerX = 0;
	private double centerY = 0;
	private double centerZ = 0;
	private boolean mouseLeftDown = false;
	private boolean mouseRightDown = false;
	private boolean mouseOver = false;
	private int mousePosX = 0;
	private int mousePosY = 0;
	private Listener listener;


	public Camera(Application app, PGraphics pg, double viewportX, double viewportY, double viewportWidth, double viewportHeight, double centerX, double centerY, double centerZ, double cameraAlpha, double cameraBeta, double cameraRadius)
	{
		this.app = app;
		this.pg = pg;
		this.viewportX = viewportX;
		this.viewportY = viewportY;
		this.viewportWidth = viewportWidth;
		this.viewportHeight = viewportHeight;

		// Set camera defaults
		this.cameraAlpha = this.cameraAlphaDefault = cameraAlpha;
		this.cameraBeta = this.cameraBetaDefault = cameraBeta;
		this.cameraRadius = this.cameraRadiusDefault = cameraRadius;
		this.centerX = this.centerXDefault = centerX; // Half volume width (negative)
		this.centerY = this.centerYDefault = centerY; // Half volume height (negative)
		this.centerZ = this.centerZDefault = centerZ; // Half volume depth
		this.cameraX = 0;
		this.cameraY = 0;
		this.cameraZ = 0;
		calculatePosition();
	}


	/**
	 * Update listener
	 *
	 * @param listener: Method to execute when values have changed
	 */
	public void onUpdate(Listener listener)
	{
		this.listener = listener;
	}


	/**
	 * Reset values
	 */
	public void reset()
	{
		cameraAlpha = cameraAlphaDefault;
		cameraBeta = cameraBetaDefault;
		cameraRadius = cameraRadiusDefault;
		centerX = centerXDefault;
		centerY = centerYDefault;
		centerZ = centerZDefault;
		cameraX = 0;
		cameraY = 0;
		cameraZ = 0;
		mouseLeftDown = false;
		mouseRightDown = false;
		mouseOver = false;
		mousePosX = 0;
		mousePosY = 0;

		// Update position
		calculatePosition();

		// Apply callback since values have changed
		if (this.listener != null) {
			this.listener.callback();
		}
	}


	/**
	 * Getters
	 */
	public double getCenterX()
	{
		return centerX;
	}

	public double getCenterY()
	{
		return centerY;
	}

	public double getCenterZ()
	{
		return centerZ;
	}

	public double getCameraX()
	{
		return cameraX;
	}

	public double getCameraY()
	{
		return cameraY;
	}

	public double getCameraZ()
	{
		return cameraZ;
	}


	/**
	 * Setters
	 */
	public void setCenterX(double centerX)
	{
		this.centerX = this.centerXDefault = centerX;
	}

	public void setCenterY(double centerY)
	{
		this.centerY = this.centerYDefault = centerY;
	}

	public void setCenterZ(double centerZ)
	{
		this.centerZ = this.centerZDefault = centerZ;
	}


	/**
	 * Check if we need to update the camera
	 */
	public void update()
	{
		// Store clicked button and last known mouse pos
		mouseLeftDown = app.mouseButton == PConstants.LEFT;
		mouseRightDown = app.mouseButton == PConstants.RIGHT;
		int mouseX = app.mouseX;
		int mouseY = app.mouseY;

		// Basic hit test
		mouseOver = (
			mouseX > viewportX && mouseX < (viewportX + viewportWidth) &&
				mouseY > viewportY && mouseY < (viewportY + viewportHeight)
		);

		// Temp values
		double alpha = cameraAlpha;
		double beta = cameraBeta;
		double radius = cameraRadius;

		// When the mouse is on top of the simulator
		// the user can move the camera or eye position
		// using the left or right mouse buttons
		if (mouseOver) {
			if (mouseLeftDown) {
				alpha -= (mousePosY - mouseY);
				beta -= (mousePosX - mouseX);
			}
			if (mouseRightDown) {
				radius -= (mousePosY - mouseY) * 2;
			}
		}

		// Check if values have changed
		if (cameraAlpha != alpha || cameraBeta != beta || cameraRadius != radius) {
			cameraAlpha = alpha;
			cameraBeta = beta;
			cameraRadius = radius;

			// Calculate camera position
			calculatePosition();

			// Apply callback since values have changed
			if (this.listener != null) {
				this.listener.callback();
			}
		}

		mousePosX = mouseX;
		mousePosY = mouseY;
	}


	/**
	 * Calculate camera position
	 */
	private void calculatePosition()
	{
		// Limit camera values
		if (cameraAlpha < 95) cameraAlpha = 95;
		if (cameraAlpha > 265) cameraAlpha = 265;
		if (cameraRadius < 1) cameraRadius = 1;
		if (cameraRadius > 1000) cameraRadius = 1000;

		// Calculate positions based on simple orb control
		cameraX = cameraRadius * Maths.cos(Maths.radians(cameraAlpha)) * Maths.cos(Maths.radians(cameraBeta));
		cameraY = cameraRadius * Maths.sin(Maths.radians(cameraAlpha));
		cameraZ = cameraRadius * Maths.cos(Maths.radians(cameraAlpha)) * Maths.sin(Maths.radians(cameraBeta));
	}


	/**
	 * Update the camera
	 */
	public void apply()
	{
		// Apply camera
		pg.camera((float) cameraX, (float) cameraY, (float) cameraZ, 0, 0, 0, 0, 1, 0);
		pg.translate((float) centerX, (float) centerY, (float) centerZ);
	}
}
