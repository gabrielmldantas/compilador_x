package br.com.ufs.xcompiler;

import br.com.ufs.xcompiler.node.InvalidToken;

public class XCompilerLexerException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private InvalidToken invalidToken;
	
	public XCompilerLexerException(InvalidToken invalidToken, String message) {
		super(message);
		this.invalidToken = invalidToken;
	}
	
	public InvalidToken getInvalidToken() {
		return invalidToken;
	}
}
