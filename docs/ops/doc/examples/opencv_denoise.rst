========================================
Fast Non-Local Means Denoise with OpenCV
========================================

In this example we will use a denoise algorithm defined in an `external libray`_: OpenCV.

This method progressively scans neighborhoods of an image looking for repeated search template. These
repeated areas can then be averaged to eliminate gaussian noise, without the requirement of additional
images for comparison.

SciJava Ops via Fiji's sripting engine with `script parameters`_:

.. tabs::

    .. code-tab:: groovy

        #@ ImgPlus img
        #@ Integer (label="strength:", value=4.0) strength
        #@ Integer (label="template size:", value=7) template
        #@ Integer (label="search size:", value=21) search
        #@output ImgPlus result

        import org.scijava.ops.api.OpEnvironment;

        // build the Ops environment
        ops = OpEnvironment.build();

        // allocate an output image
        result = img.copy();

        // run the denoise op
        ops.quaternary("filter.denoise").input(img, strength, template, search).output(result).compute();

.. _`script parameters`: https://imagej.net/scripting/parameters
.. _`external libray`: https://docs.opencv.org/4.x/d5/d69/tutorial_py_non_local_means.html
