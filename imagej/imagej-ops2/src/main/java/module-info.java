/*-
 * #%L
 * ImageJ2 software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2014 - 2022 ImageJ2 developers.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
module net.imagej.ops2 {
	exports net.imagej.ops2;
	
	// -- Open plugins to scijava-ops
	opens net.imagej.ops2.adapt to org.scijava.ops.engine;
	opens net.imagej.ops2.adapt.complexLift to org.scijava.ops.engine;
	opens net.imagej.ops2.coloc to org.scijava.ops.engine;
	opens net.imagej.ops2.coloc.icq to org.scijava.ops.engine;
	opens net.imagej.ops2.coloc.kendallTau to org.scijava.ops.engine;
	opens net.imagej.ops2.coloc.maxTKendallTau to org.scijava.ops.engine;
	opens net.imagej.ops2.coloc.pearsons to org.scijava.ops.engine;
	opens net.imagej.ops2.coloc.pValue to org.scijava.ops.engine;
	opens net.imagej.ops2.convert to org.scijava.ops.engine;
	opens net.imagej.ops2.convert.clip to org.scijava.ops.engine;
	opens net.imagej.ops2.convert.copy to org.scijava.ops.engine;
	opens net.imagej.ops2.copy to org.scijava.ops.engine;
	opens net.imagej.ops2.create to org.scijava.ops.engine;
	opens net.imagej.ops2.deconvolve to org.scijava.ops.engine;
	opens net.imagej.ops2.deconvolve.accelerate to org.scijava.ops.engine;
	opens net.imagej.ops2.eval to org.scijava.ops.engine;
	opens net.imagej.ops2.features.haralick to org.scijava.ops.engine;
	opens net.imagej.ops2.features.haralick.helper to org.scijava.ops.engine;
	opens net.imagej.ops2.features.hog to org.scijava.ops.engine;
	opens net.imagej.ops2.features.lbp2d to org.scijava.ops.engine;
	opens net.imagej.ops2.features.tamura2d to org.scijava.ops.engine;
	opens net.imagej.ops2.features.zernike to org.scijava.ops.engine;
	opens net.imagej.ops2.filter to org.scijava.ops.engine;
	opens net.imagej.ops2.filter.addNoise to org.scijava.ops.engine;
	opens net.imagej.ops2.filter.bilateral to org.scijava.ops.engine;
	opens net.imagej.ops2.filter.convolve to org.scijava.ops.engine;
	opens net.imagej.ops2.filter.correlate to org.scijava.ops.engine;
	opens net.imagej.ops2.filter.derivative to org.scijava.ops.engine;
	opens net.imagej.ops2.filter.derivativeGauss to org.scijava.ops.engine;
	opens net.imagej.ops2.filter.dog to org.scijava.ops.engine;
	opens net.imagej.ops2.filter.fft to org.scijava.ops.engine;
	opens net.imagej.ops2.filter.fftSize to org.scijava.ops.engine;
	opens net.imagej.ops2.filter.gauss to org.scijava.ops.engine;
	opens net.imagej.ops2.filter.hessian to org.scijava.ops.engine;
	opens net.imagej.ops2.filter.ifft to org.scijava.ops.engine;
	opens net.imagej.ops2.filter.pad to org.scijava.ops.engine;
	opens net.imagej.ops2.filter.sigma to org.scijava.ops.engine;
	opens net.imagej.ops2.filter.sobel to org.scijava.ops.engine;
	opens net.imagej.ops2.filter.tubeness to org.scijava.ops.engine;
	opens net.imagej.ops2.filter.vesselness to org.scijava.ops.engine;
	opens net.imagej.ops2.geom to org.scijava.ops.engine;
	opens net.imagej.ops2.geom.geom2d to org.scijava, org.scijava.ops.engine;
	opens net.imagej.ops2.geom.geom3d to org.scijava.ops.engine;
	opens net.imagej.ops2.identity to org.scijava.ops.engine;
	opens net.imagej.ops2.image.ascii to org.scijava.ops.engine;
	opens net.imagej.ops2.image.cooccurrenceMatrix to org.scijava.ops.engine;
	opens net.imagej.ops2.image.distancetransform to org.scijava.ops.engine;
	opens net.imagej.ops2.image.fill to org.scijava.ops.engine;
	opens net.imagej.ops2.image.histogram to org.scijava.ops.engine;
	opens net.imagej.ops2.image.integral to org.scijava.ops.engine;
	opens net.imagej.ops2.image.invert to org.scijava.ops.engine;
	opens net.imagej.ops2.image.normalize to org.scijava.ops.engine;
	opens net.imagej.ops2.image.watershed to org.scijava.ops.engine;
	opens net.imagej.ops2.imagemoments to org.scijava.ops.engine;
	opens net.imagej.ops2.imagemoments.centralmoments to org.scijava.ops.engine;
	opens net.imagej.ops2.imagemoments.hu to org.scijava.ops.engine;
	opens net.imagej.ops2.imagemoments.moments to org.scijava.ops.engine;
	opens net.imagej.ops2.imagemoments.normalizedcentralmoments to org.scijava.ops.engine;
	opens net.imagej.ops2.labeling to org.scijava.ops.engine;
	opens net.imagej.ops2.labeling.cca to org.scijava.ops.engine;
	opens net.imagej.ops2.linalg.rotate to org.scijava.ops.engine;
	opens net.imagej.ops2.logic to org.scijava.ops.engine;
	opens net.imagej.ops2.map.neighborhood to org.scijava.ops.engine;
	opens net.imagej.ops2.math to org.scijava.ops.engine;
	opens net.imagej.ops2.math.multiply to org.scijava.ops.engine;
	opens net.imagej.ops2.morphology to org.scijava.ops.engine;
	opens net.imagej.ops2.morphology.thin to org.scijava.ops.engine;
	opens net.imagej.ops2.project to org.scijava.ops.engine;
	opens net.imagej.ops2.segment.detectJunctions to org.scijava.ops.engine;
	opens net.imagej.ops2.segment.detectRidges to org.scijava.ops.engine;
	opens net.imagej.ops2.slice to org.scijava.ops.engine;
	opens net.imagej.ops2.stats to org.scijava.ops.engine;
	opens net.imagej.ops2.stats.regression.leastSquares to org.scijava.ops.engine;
	opens net.imagej.ops2.thread.chunker to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.apply to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.huang to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.ij1 to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.intermodes to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.isoData to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.li to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.localBernsen to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.localContrast to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.localMean to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.localMedian to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.localMidGrey to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.localNiblack to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.localPhansalkar to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.localSauvola to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.maxEntropy to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.maxLikelihood to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.mean to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.minError to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.minimum to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.moments to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.otsu to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.percentile to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.renyiEntropy to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.rosin to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.shanbhag to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.triangle to org.scijava.ops.engine;
	opens net.imagej.ops2.threshold.yen to org.scijava.ops.engine;
	opens net.imagej.ops2.topology to org.scijava.ops.engine;
	opens net.imagej.ops2.topology.eulerCharacteristic to org.scijava.ops.engine;
	opens net.imagej.ops2.transform to org.scijava.ops.engine;
	opens net.imagej.ops2.types to org.scijava.ops.engine, org.scijava.types;
	opens net.imagej.ops2.types.adapt to org.scijava.ops.engine;
	opens net.imagej.ops2.types.maxValue to org.scijava.ops.engine;
	opens net.imagej.ops2.types.minValue to org.scijava.ops.engine;

	requires java.scripting;
	requires net.imagej.mesh2;
	requires net.imglib2;
	requires net.imglib2.algorithm;
	requires net.imglib2.algorithm.fft2;
	requires net.imglib2.roi;
	requires org.joml;
	requires org.scijava.collections;
	requires org.scijava.concurrent;
	requires org.scijava.function;
	requires org.scijava.meta;
	requires org.scijava.ops.api;
	requires org.scijava.ops.engine;
	requires org.scijava.ops.spi;
	requires org.scijava.parsington;
	requires org.scijava.priority;
	requires org.scijava.types;
	
	// FIXME: these module names derive from filenames and are thus unstable
	requires commons.math3;
	requires ojalgo;
	requires jama;
	requires mines.jtk;
	requires net.imglib2.realtransform;

	provides org.scijava.types.TypeExtractor with
			net.imagej.ops2.types.ImgFactoryTypeExtractor,
			net.imagej.ops2.types.ImgLabelingTypeExtractor,
			net.imagej.ops2.types.NativeImgTypeExtractor,
			net.imagej.ops2.types.LabelingMappingTypeExtractor,
			net.imagej.ops2.types.OutOfBoundsConstantValueFactoryTypeExtractor,
			net.imagej.ops2.types.OutOfBoundsFactoryTypeExtractor,
			net.imagej.ops2.types.OutOfBoundsRandomValueFactoryTypeExtractor,
			net.imagej.ops2.types.RAITypeExtractor;

	uses org.scijava.ops.api.features.MatchingRoutine;
}
