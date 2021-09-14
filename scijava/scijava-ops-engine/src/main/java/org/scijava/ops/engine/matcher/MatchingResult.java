package org.scijava.ops.engine.matcher;

import java.util.ArrayList;
import java.util.List;

import org.scijava.ops.engine.OpCandidate;
import org.scijava.ops.engine.OpRef;
import org.scijava.ops.engine.OpUtils;
import org.scijava.ops.engine.OpCandidate.StatusCode;

/**
 * Class representing the result from type matching done by the
 * {@link OpMatcher}. Contains the original candidates which match
 * the types specified by {@link OpRef} and the final matches that match all
 * inputs, outputs, and arguments.
 * 
 * @author David Kolb
 */
public class MatchingResult {

	private final List<OpCandidate> candidates;
	private final List<OpCandidate> matches;
	private final List<OpRef> originalQueries;

	public static MatchingResult empty(final List<OpRef> originalQueries) {
		return new MatchingResult(new ArrayList<OpCandidate>(), new ArrayList<OpCandidate>(), originalQueries);
	}
	
	public MatchingResult(final List<OpCandidate> candidates, final List<OpCandidate> matches, final List<OpRef> originalQueries) {
		this.candidates = candidates;
		this.matches = matches;
		this.originalQueries = originalQueries;
	}

	public List<OpRef> getOriginalQueries() {
		return originalQueries;
	}

	public List<OpCandidate> getCandidates() {
		return candidates;
	}

	public List<OpCandidate> getMatches() {
		return matches;
	}

	public OpCandidate singleMatch() {
		if (matches.size() == 1) {
			// if (log.isDebug()) {
			// log.debug("Selected '" + match.getRef().getLabel() + "' op: " +
			// match.opInfo().opClass().getName());
			// }

			// TODO: DO we still need this initialization?
			// // initialize the op, if appropriate
			// if (m.object() instanceof Initializable) {
			// ((Initializable) m.object()).initialize();
			// }

			return matches.get(0);
		}

		// There is no clear matching Op
		final String analysis = MatchingResult.matchInfo(this);
		throw new OpMatchingException(analysis);
	}

	/**
	 * Gets a string with an analysis of a particular match request failure.
	 * <p>
	 * This method is used to generate informative exception messages when no
	 * matches, or too many matches, are found.
	 * </p>
	 * 
	 * @param res
	 *            The result of type matching
	 * @return A multi-line string describing the situation: 1) the type of
	 *         match failure; 2) the list of matching ops (if any); 3) the
	 *         request itself; and 4) the list of candidates including status
	 *         (i.e., whether it matched, and if not, why not).
	 */
	public static String matchInfo(final MatchingResult res) {
		final StringBuilder sb = new StringBuilder();
	
		List<OpCandidate> candidates = res.getCandidates();
		List<OpCandidate> matches = res.getMatches();
	
		final OpRef ref = res.getOriginalQueries().get(0);
		if (matches.isEmpty()) {
			// no matches
			sb.append("No matching '" + ref.getLabel() + "' op\n");
		} else {
			// multiple matches
			final double priority = OpUtils.getPriority(matches.get(0));
			sb.append("Multiple '" + ref.getLabel() + "' ops of priority " + priority + ":\n");
			if (OpUtils.typeCheckingIncomplete(matches)) {
				sb.append("Incomplete output type checking may have occured!\n");
			}
			int count = 0;
			for (final OpCandidate match : matches) {
				sb.append(++count + ". ");
				sb.append(match.toString() + "\n");
			}
		}
	
		// fail, with information about the request and candidates
		sb.append("\n");
		sb.append("Request:\n");
		sb.append("-\t" + ref.toString() + "\n");
		sb.append("\n");
		sb.append("Candidates:\n");
		if (candidates.isEmpty()) {
			sb.append("-\t No candidates found!");
		}
		int count = 0;
		for (final OpCandidate candidate : candidates) {
			sb.append(++count + ". ");
			sb.append("\t" + OpUtils.opString(candidate.opInfo(), candidate.getStatusItem()) + "\n");
			final String status = candidate.getStatus();
			if (status != null)
				sb.append("\t" + status + "\n");
			if (candidate.getStatusCode() == StatusCode.DOES_NOT_CONFORM) {
				// TODO: Conformity not yet implemented
				// // show argument values when a contingent op rejects them
				// for (final ModuleItem<?> item : inputs(info)) {
				// final Object value = item.getValue(candidate.getModule());
				// sb.append("\t\t" + item.getName() + " = " + value + "\n");
				// }
			}
		}
		return sb.toString();
	}
}
