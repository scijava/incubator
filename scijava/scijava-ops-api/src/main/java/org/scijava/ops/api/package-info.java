/**
 * This module contains the API necessary to retrieve, execute, and reason about Ops.
 *
 * <h1>What is the SciJava Ops Library?</h1>
 * SciJava Ops arose from a desire for simplicity - there are lots of different algorithmic libraries, each with their own data structures, algorithm syntax, and other requirements. With all of these differences, library interoperability can be a daunting task. This can lead to algorithm reimplementations, unmaintained code, and a lack of cooperation between open-source communitites. SciJava Ops seeks to unify different libraries under a single framework, separating the development of new algorithms from the way they are executed.
 *
 * Put more succinctly, the goals of SciJava Ops are the following:
 * <ul>
 *   <li><b>Usability</b> All Ops should be called in the same way, and implementation details should be hidden - this means that the user should call an Op the same way, whether the algorithm is written in Java, Python, or as a CUDA kernel.</li>
 *   <li><b>Extensibility</b> New Ops should be accommodated in a variety of programming languages, and each language should provide minimally-invasive methods for declaring code blocks as Ops. This also means that Ops should accommodate writing Ops in one language, and then calling Ops using data structures from a different language.</li>
 *   <li><b>Generality</b> Ops should allow any number of typed inputs, without restriction on input types. With no restrictions on input types, the library can seamlessly adapt new data structures tailored to performance or expressiveness as they are developed.</li>
 *   <li><b>Speed</b> Calling Ops should be approximately as performant as invocation in their natural setting (such as Python, C++, Java, or another language entirely).</li>
 * </ul>
 *
 * <h1>What is an Op?</h1>
 *
 * An Op is an algorithm adhering to the following traits:
 * <ol>
 * <li>Ops are stateless and deterministic - with no internal state, calling an Op two times on the same inputs will produce the same output.</li>
 * <li>Ops are named - this name conveys an Op's purpose, and allows us to find all Ops implementing a particular operation</li>
 * </ol>
 * Using the name and the combination of input and output parameters, we can retrieve, or "match", any Op from within an {@link org.scijava.ops.api.OpEnvironment}. Op calls with the same name and specified inputs/outputs will be reproducible within a particular Op environment.
 *
 * <h1>Op Equivalence</h1>
 * To support the Op matching paradigm, we establish three types of equivalence:
 * <ol>
 *   <li><b>Form Equivalence</b></li> Form Equivalence implies that two objects (which could be Ops, or Op parameters) theoretically draw from the same shared idea (such as an addition operation, or an image, etc.)
 *   <li><b>Structural Equivalence</b></li> Structural Equivalence means that two Ops:
 *   <ol>
 *     <li>Are Form Equivalent</li>
 *     <li>Have the same number of inputs and the same number of outputs</li>
 *     <li>For each input position, accept form-equivalent inputs</li>
 *     <li>Return form-equivalent outputs</li>
 *   </ol>
 *   <li><b>Result Equivalence</b></li> Result Equivalence means that {@code o1.equals(o2)} for two outputs {@code o1} and {@code o2} from two Ops.
 * </ol>
 * Within the Ops API, each type of equivalence is utilized in the following ways:
 * <ol>
 *   <li><b>Form Equivalence</b></li> If two Ops are form-equivalent, they are defined under the same name.
 *   <li><b>Structural Equivalence</b></li> If two Ops are structural-equivalent, they share a common form-reduced description, searchable using {@link org.scijava.ops.api.OpEnvironment#descriptions(java.lang.String)}. For example, an Op "math.add" that produces an ImgLib2 Img from Img operands, and another Op "math.add" that produces a NumPy ndarray from ndarray operands, will reduce to a single description "math.add" that produces an image from image operands.
 *   <li><b>Result Equivalence</b></li> If two Ops are result-equivalent, then they are considered to produce equivalent values, using the primary language-specific definition of equality (e.g. `Object.equals`, for Java usage).
 * </ol>
 *
 * For example, consider three Ops:
 * <ol>
 *   <li> filter.gauss(net.imglib2.img.Img, net.imglib2.type.numeric.real.FloatType) -> net.imglib2.img.Img</li>
 *   <li> filter.gauss(ij.ImagePlus, java.lang.Double) -> ij.ImagePlus</li>
 *   <li> filter.gauss(net.imglib2.img.Img, net.imglib2.algorithm.neighborhood.Shape) -> net.imglib2.img.Img</li>
 * </ol>
 * Ops 1 and 2 should be considered form-equivalent, as they have the same name, and structural-equivalent, as they both take in an image data structure and a floating point number and return an image data structure, but they are likely not result-equivalent due to the underlying data structures.
 * <p>
 * Ops 1 and 3 should also be considered form-equivalent, as they have the same name, but are not structural-equivalent, as one computes its own Shape over which to perform a gaussian blur, while the other takes a predefined shape.
 * <p>
 * These definitions of equivalence provide a level of flexibility impossible without the Ops API; structural-equivalence allows us to, for example, define equivalent Ops across programming languages and libraries, and then to create scripts that can run unaltered on data types from each of those languages and libraries. Given the same {@link org.scijava.ops.api.OpEnvironment} and inputs, however, we ensure result-equivalence and therefore reproducability
 *
 */
package org.scijava.ops.api;
