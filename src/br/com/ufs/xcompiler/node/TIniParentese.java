/* This file was generated by SableCC (http://www.sablecc.org/). */

package br.com.ufs.xcompiler.node;

import br.com.ufs.xcompiler.analysis.*;

@SuppressWarnings("nls")
public final class TIniParentese extends Token
{
    public TIniParentese()
    {
        super.setText("(");
    }

    public TIniParentese(int line, int pos)
    {
        super.setText("(");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TIniParentese(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTIniParentese(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TIniParentese text.");
    }
}
