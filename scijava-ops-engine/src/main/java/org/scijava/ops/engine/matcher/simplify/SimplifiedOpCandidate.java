package org.scijava.ops.engine.matcher.simplify;

import org.scijava.ops.api.OpEnvironment;
import org.scijava.ops.api.OpInfo;
import org.scijava.ops.api.OpRequest;
import org.scijava.ops.engine.OpCandidate;
import org.scijava.ops.spi.Op;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;

public class SimplifiedOpCandidate extends OpCandidate {

	private final OpRequest request;
	private final OpInfo info;

	public SimplifiedOpCandidate(OpEnvironment env, OpRequest request, SimplifiedOpInfo simpleInfo, Map<TypeVariable<?>, Type> typeVarAssigns) {
		super(env, request, simpleInfo, typeVarAssigns);
		if (request instanceof SimplifiedOpRequest) {
			SimplifiedOpRequest simpleRequest = (SimplifiedOpRequest) request;
			this.request = simpleRequest.srcReq();
			info = new FocusedOpInfo(simpleInfo, simpleRequest, env);
		}
		else {
			this.request = request;
			info = simpleInfo;
		}
	}

	@Override
	public OpRequest getRequest() {
		return request;
	}

	@Override
	public OpInfo opInfo() {
		return info;
	}

}
