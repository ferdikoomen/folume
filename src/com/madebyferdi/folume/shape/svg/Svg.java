package com.madebyferdi.folume.shape.svg;


import com.madebyferdi.folume.Application;
import com.madebyferdi.folume.utils.Maths;
import com.madebyferdi.folume.utils.Utils;
import processing.core.PGraphics;
import processing.core.PShape;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


final public class Svg
{

	// Regexp to parse SVG
	final static private Pattern PATTERN_GROUPS = Pattern.compile("<path.*d=\"(.*?)\".*/>", Pattern.CASE_INSENSITIVE);
	final static private Pattern PATTERN_COMMANDS = Pattern.compile("([MLHVCSAZ])([\\-\\d.,]+)?", Pattern.CASE_INSENSITIVE);
	final static private Pattern PATTERN_COORDINATES = Pattern.compile("(-?[\\d.]+)");


	// Scale from points to millimeter
	final static private double SCALE = 2.83464567f;


	// SVG commands
	final static private String MOVE = "M";
	final static private String MOVE_RELATIVE = "m";
	final static private String CLOSE_PATH = "Z";
	final static private String CLOSE_PATH_RELATIVE = "z";
	final static private String LINE = "L";
	final static private String LINE_RELATIVE = "l";
	final static private String LINE_HORIZONTAL = "H";
	final static private String LINE_HORIZONTAL_RELATIVE = "h";
	final static private String LINE_VERTICAL = "V";
	final static private String LINE_VERTICAL_RELATIVE = "v";
	final static private String CURVE = "C";
	final static private String CURVE_RELATIVE = "c";
	final static private String SMOOTH_CURVE = "S";
	final static private String SMOOTH_CURVE_RELATIVE = "s";
	final static private String QUADRATIC_BEZIER = "Q";
	final static private String QUADRATIC_BEZIER_RELATIVE = "q";
	final static private String SMOOTH_QUADRATIC_BEZIER = "T";
	final static private String SMOOTH_QUADRATIC_BEZIER_RELATIVE = "t";
	final static private String ELLIPTICAL_ARC = "A";
	final static private String ELLIPTICAL_ARC_RELATIVE = "a";


	// Properties
	private Application app;
	private List<SvgPath> paths;
	private SvgPath path;
	private PShape ps;
	private double minX = 10000;
	private double minY = 10000;
	private double maxX = -10000;
	private double maxY = -10000;
	private double width = 0;
	private double height = 0;


	public Svg(Application app)
	{
		this.app = app;
		this.paths = new ArrayList<>();
	}


	/**
	 * Return true if we have data
	 */
	public boolean isLoaded()
	{
		return !paths.isEmpty();
	}


	/**
	 * Getters
	 */
	public double getMinX()
	{
		return minX;
	}

	public double getMinY()
	{
		return minY;
	}

	public double getMaxX()
	{
		return maxX;
	}

	public double getMaxY()
	{
		return maxY;
	}

	public double getWidth()
	{
		return width;
	}

	public double getHeight()
	{
		return height;
	}


	/**
	 * Unload file
	 */
	public void unload()
	{
		paths.clear();
		path = null;
		minX = 10000;
		minY = 10000;
		maxX = -10000;
		maxY = -10000;
		width = 0;
		height = 0;
	}


	/**
	 * Load a SVG file
	 *
	 * @param filename: File to load
	 */
	public void load(String filename)
	{
		// Clear current data
		paths.clear();
		path = new SvgPath();

		// Read the content
		String lines[] = app.loadStrings(filename);

		// Current positions
		double x = 0;
		double y = 0;

		// Iterate over the lines and find the paths
		for (String line : lines) {

			// Fina all the paths
			Matcher groups = PATTERN_GROUPS.matcher(line);
			if (groups.find()) {

				// Grab path and remove all spaces
				String group = groups.group(1);
				group = group.replaceAll("\\s", "");

				// Find all command groups in the path
				Matcher commands = PATTERN_COMMANDS.matcher(group);
				while (commands.find()) {

					// Get the command code and the data
					String code = commands.group(1);
					String payload = commands.group(2);

					// Load all the coordinates from the data
					List<Double> values = new ArrayList<>();
					if (payload != null) {
						Matcher coordinates = PATTERN_COORDINATES.matcher(payload);
						while (coordinates.find()) {
							values.add(Utils.getDouble(coordinates.group(1)));
						}
					}

					// Check if we are dealing with absolute or relative coordinates
					boolean absolute = (code.toUpperCase().equals(code));

					// Check commands
					switch (code) {

						case MOVE:
						case MOVE_RELATIVE:
							x = absolute ? values.get(0) : x + values.get(0);
							y = absolute ? values.get(1) : y + values.get(1);
							path = new SvgPath();
							paths.add(path);
							addPoint(x, y);
							break;

						case CLOSE_PATH:
						case CLOSE_PATH_RELATIVE:
							// Ignore since the item is already added
							break;

						case LINE:
						case LINE_RELATIVE:
							x = absolute ? values.get(0) : x + values.get(0);
							y = absolute ? values.get(1) : y + values.get(1);
							addPoint(x, y);
							break;

						case LINE_HORIZONTAL:
						case LINE_HORIZONTAL_RELATIVE:
							for (Double value : values) {
								x = absolute ? value : x + value;
								addPoint(x, y);
							}
							break;

						case LINE_VERTICAL:
						case LINE_VERTICAL_RELATIVE:
							for (Double value : values) {
								y = absolute ? value : y + value;
								addPoint(x, y);
							}
							break;

						case CURVE:
						case CURVE_RELATIVE:

							for (int i = 0; i < values.size(); i += 6) {

								// Points of the bezier curve
								List<SvgPoint> points = SvgBezier.getPoints(x, y, absolute ? values.get(i) : x + values.get(i), absolute ? values.get(i + 1) : y + values.get(i + 1), absolute ? values.get(i + 2) : x + values.get(i + 2), absolute ? values.get(i + 3) : y + values.get(i + 3), absolute ? values.get(i + 4) : x + values.get(i + 4), absolute ? values.get(i + 5) : y + values.get(i + 5), 100);

								// Store points
								for (SvgPoint point : points) {
									x = point.getX();
									y = point.getY();
									addPoint(x, y);
								}
							}
							break;

						case SMOOTH_CURVE:
						case SMOOTH_CURVE_RELATIVE:
						case QUADRATIC_BEZIER:
						case QUADRATIC_BEZIER_RELATIVE:
						case SMOOTH_QUADRATIC_BEZIER:
						case SMOOTH_QUADRATIC_BEZIER_RELATIVE:
						case ELLIPTICAL_ARC:
						case ELLIPTICAL_ARC_RELATIVE:
							// System.out.println("Svg: Unsupported command");
							break;
					}
				}
			}
		}

		calculateSize();
	}


	/**
	 * Add point and convert to MM
	 *
	 * @param x: X position
	 * @param y: Y position
	 */
	private void addPoint(double x, double y)
	{
		path.add(x / SCALE, y / SCALE);
	}


	/**
	 * Calculate size
	 */
	private void calculateSize()
	{
		// Reset current size
		minX = 10000;
		minY = 10000;
		maxX = -10000;
		maxY = -10000;
		width = 0;
		height = 0;

		// Calculate bounding box
		for (SvgPath path : paths) {
			for (SvgPoint point : path.getPoints()) {
				double x = point.getX();
				double y = point.getY();
				if (x < minX) minX = x;
				if (y < minY) minY = y;
				if (x > maxX) maxX = x;
				if (y > maxY) maxY = y;
			}
		}

		// Calculate size
		width = Maths.abs(maxX - minX);
		height = Maths.abs(maxY - minY);
	}


	/**
	 * Draw SVG shape to graphics layer
	 *
	 * @param scale: Scale factor
	 * @param pg:    Graphics object
	 */
	public void draw(PGraphics pg, double scale)
	{
		// Create new shape
		ps = pg.createShape();

		// Draw groups and paths
		for (SvgPath path : paths) {
			ps.beginShape();
			for (SvgPoint point : path.getPoints()) {
				ps.vertex((float) ((point.getX() - minX) * scale), (float) ((point.getY() - minY) * scale));
			}
			ps.endShape();
		}

		ps.draw(pg);
	}
}
