/* This file was generated by SableCC (http://www.sablecc.org/). */

package br.com.ufs.xcompiler.node;

import br.com.ufs.xcompiler.analysis.*;

@SuppressWarnings("nls")
public final class TFunction extends Token
{
    public TFunction(String text)
    {
        setText(text);
    }

    public TFunction(String text, int line, int pos)
    {
        setText(text);
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TFunction(getText(), getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTFunction(this);
    }
}
