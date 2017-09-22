package com.madebyferdi.folume.shape;


import com.madebyferdi.folume.Application;
import com.madebyferdi.folume.shape.svg.Svg;
import com.madebyferdi.folume.utils.Listener;

import java.util.ArrayList;
import java.util.List;

final public class Shape
{


	// Properties
	private Svg svg;
	private ShapeCurve curveOutside;
	private ShapeCurve curveInside;
	private List<ShapePathPoint> pathDesignTemp;
	private ShapePath pathDesign;
	private List<ShapePathPoint> pathPrintTemp;
	private ShapePath pathPrint;
	private int numLayersTemp = 0;
	private int numLayers = 0;
	private int numPointsTemp = 0;
	private int numPoints = 0;
	private double totalDistanceTemp = 0;
	private double totalDistance = 0;
	private double totalFilamentTemp = 0;
	private double totalFilament = 0;
	private double totalDurationTemp = 0;
	private double totalDuration = 0;
	final private Object lock = new Object();
	private boolean syncDesign = false;
	private boolean syncPrint = false;
	private Listener listener;


	// Design properties
	private double _height = 0;
	private double _degrees = 0;
	private double _xTop = 0;
	private double _xBottom = 0;
	private double _yTop = 0;
	private double _yBottom = 0;
	private double _radiusInsideTop = 0;
	private double _radiusInsideBottom = 0;
	private double _radiusOutsideTop = 0;
	private double _radiusOutsideBottom = 0;
	private double _ribCount = 0;
	private double _ribWidthInsideTop = 0;
	private double _ribWidthInsideBottom = 0;
	private double _ribWidthOutsideTop = 0;
	private double _ribWidthOutsideBottom = 0;
	private double _rotateInsideTop = 0;
	private double _rotateInsideBottom = 0;
	private double _rotateOutsideTop = 0;
	private double _rotateOutsideBottom = 0;
	private double _speedTravel = 0;
	private double _speedPrint = 0;
	private double _speedZ = 0;
	private double _filamentDiameter = 0;
	private double _nozzleDiameter = 0;
	private double _layerHeight = 0;
	private double _layerWidth = 0;
	private double _layerCount = 0;


	public Shape(Application app)
	{
		svg = new Svg(app);
		curveOutside = new ShapeCurve();
		curveInside = new ShapeCurve();
		pathDesignTemp = new ArrayList<>();
		pathDesign = new ShapePath();
		pathPrintTemp = new ArrayList<>();
		pathPrint = new ShapePath();
	}


	/**
	 * Getters
	 */
	public Svg getSvg()
	{
		return svg;
	}

	public ShapeCurve getCurveOutside()
	{
		return curveOutside;
	}

	public ShapeCurve getCurveInside()
	{
		return curveInside;
	}

	public ShapePath getPathDesign()
	{
		return pathDesign;
	}

	public ShapePath getPathPrint()
	{
		return pathPrint;
	}

	public int getNumLayers()
	{
		return numLayers;
	}

	public int getNumPoints()
	{
		return numPoints;
	}

	public double getTotalDistance()
	{
		return totalDistance;
	}

	public double getTotalFilament()
	{
		return totalFilament;
	}

	public double getTotalDuration()
	{
		return totalDuration;
	}


	/**
	 * Change listener
	 *
	 * @param listener: Method to call when value is changed
	 */
	public void onUpdate(Listener listener)
	{
		this.listener = listener;
	}


	/**
	 * The update method checks if we need to synchronize the
	 * temporary values with the final output values. This method
	 * is called from the main loop, before any other processing
	 * can take place. However other processes might be updating
	 * the design, so we need to make sure that we lock any shared
	 * data like the temp paths.
	 */
	public void update()
	{
		if (syncDesign) {
			syncDesign = false;

			synchronized (lock) {
				numLayers = numLayersTemp;
				numPoints = numPointsTemp;
				pathDesign.clear();
				for (ShapePathPoint p : pathDesignTemp) {
					pathDesign.add(
						p.getX(),
						p.getY(),
						p.getZ(),
						p.getLayer()
					);
				}
			}
		}

		if (syncPrint) {
			syncPrint = false;

			synchronized (lock) {
				totalDistance = totalDistanceTemp;
				totalFilament = totalFilamentTemp;
				totalDuration = totalDurationTemp;
				pathPrint.clear();
				for (ShapePathPoint p : pathPrintTemp) {
					pathPrint.add(
						p.getX(),
						p.getY(),
						p.getZ(),
						p.getDistance(),
						p.getTotalDistance(),
						p.getFilament(),
						p.getTotalFilament(),
						p.getDuration(),
						p.getTotalDuration(),
						p.getSpeed(),
						p.getLayer()
					);
				}
			}
		}
	}


	/**
	 * Generate design
	 */
	public void generateDesign(double height, double steps, double degrees, double xTop, double yTop, double xBottom, double yBottom, double radiusInsideTop, double radiusInsideBottom, double radiusOutsideTop, double radiusOutsideBottom, double ribCount, double ribWidthInsideTop, double ribWidthInsideBottom, double ribWidthOutsideTop, double ribWidthOutsideBottom, double rotateInsideTop, double rotateInsideBottom, double rotateOutsideTop, double rotateOutsideBottom)
	{
		// Save settings
		this._height = height;
		this._degrees = degrees;
		this._xTop = xTop;
		this._yTop = yTop;
		this._xBottom = xBottom;
		this._yBottom = yBottom;
		this._radiusInsideTop = radiusInsideTop;
		this._radiusInsideBottom = radiusInsideBottom;
		this._radiusOutsideTop = radiusOutsideTop;
		this._radiusOutsideBottom = radiusOutsideBottom;
		this._ribCount = ribCount;
		this._ribWidthInsideTop = ribWidthInsideTop;
		this._ribWidthInsideBottom = ribWidthInsideBottom;
		this._ribWidthOutsideTop = ribWidthOutsideTop;
		this._ribWidthOutsideBottom = ribWidthOutsideBottom;
		this._rotateInsideTop = rotateInsideTop;
		this._rotateInsideBottom = rotateInsideBottom;
		this._rotateOutsideTop = rotateOutsideTop;
		this._rotateOutsideBottom = rotateOutsideBottom;

		// Get the number of layers and calculate the number
		// of points per layer: Each segment has 6 point, however
		// the last point of a segment is shared with the first
		// point of the next segment, therefore we have 5 points
		// per segment, plus 1 for the last segment in the layer.
		// These values are needed to calculate the quads in the
		// design preview area.
		numLayersTemp = (int) steps + 1;
		numPointsTemp = (int) (ribCount * 6) + 1;

		synchronized (lock) {

			// Clear path
			pathDesignTemp.clear();

			// Draw shape
			ShapeGenerator.draw(
				curveInside,
				curveOutside,
				pathDesignTemp,
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
		}

		// Once the design is updated we should call the
		// update handler to trigger other parts
		if (listener != null) {
			listener.callback();
		}

		// Set flag
		syncDesign = true;
	}


	/**
	 * Generate print version
	 */
	public void generatePrint(double speedTravel, double speedPrint, double speedZ, double filamentDiameter, double nozzleDiameter, double layerHeight, double layerWidth)
	{
		// Save settings
		this._speedTravel = speedTravel;
		this._speedPrint = speedPrint;
		this._speedZ = speedZ;
		this._filamentDiameter = filamentDiameter;
		this._nozzleDiameter = nozzleDiameter;
		this._layerHeight = layerHeight;
		this._layerWidth = layerWidth;
		this._layerCount = _height / layerHeight;

		synchronized (lock) {

			// We create a new list object, since the points that
			// the shape generator makes are not optimized for
			// printing, but rather for quick 3D generation.
			List<ShapePathPoint> points = new ArrayList<>();

			// First we draw the basic shape, but with a lot
			// more steps since we know have a step for each
			// print layer.
			ShapeGenerator.draw(
				curveInside,
				curveOutside,
				points,
				_height,
				_layerCount,
				_degrees,
				_xTop, _yTop,
				_xBottom, _yBottom,
				_radiusInsideTop, _radiusInsideBottom,
				_radiusOutsideTop, _radiusOutsideBottom,
				_ribCount,
				_ribWidthInsideTop, _ribWidthInsideBottom,
				_ribWidthOutsideTop, _ribWidthOutsideBottom,
				_rotateInsideTop, _rotateInsideBottom,
				_rotateOutsideTop, _rotateOutsideBottom
			);

			// Clear path
			pathPrintTemp.clear();

			// Now we are done generating the shape, we can optimize
			// the path for printing. This will include optimizing
			// steps for z-hops and adding additional data like
			// speed, material length etc.
			ShapeOptimizer.optimize(
				points,
				pathPrintTemp,
				_speedTravel,
				_speedPrint,
				_speedZ,
				_filamentDiameter,
				_nozzleDiameter,
				_layerHeight,
				_layerWidth
			);

			// Store properties
			if (!pathPrintTemp.isEmpty()) {
				ShapePathPoint p = pathPrintTemp.get(pathPrintTemp.size() - 1);
				totalDistanceTemp = p.getTotalDistance();
				totalFilamentTemp = p.getTotalFilament();
				totalDurationTemp = p.getTotalDuration();
			}
		}

		// Set flag
		syncPrint = true;
	}


	/**
	 * Generate bounding circle
	 */
	public List<ShapePathPoint> generateBounds()
	{
		// Get size
		double width = pathDesign.getWidth() + 10; // Plus 10 millimeter
		double depth = pathDesign.getDepth() + 10; // Plus 10 millimeter

		// Draw shape
		return ShapeBounds.generate(
			width,
			depth,
			_speedPrint,
			_filamentDiameter,
			_nozzleDiameter,
			_layerHeight,
			_layerWidth
		);
	}
}
