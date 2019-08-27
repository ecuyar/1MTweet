package edu.ceng;

import com.lexicalscope.jewel.cli.Option;
import com.lexicalscope.jewel.cli.Unparsed;

/**
 * Created by MONSTER on 23.12.2017.
 */
public interface CommandArgs {
    @Option(shortName ="e",defaultValue = "hashtag",longName = "entity")
    String getEntity();
    @Option(shortName ="i",longName = "ignore-case")
    boolean getIsIgnore();
    @Option(shortName ="r",longName = "reverse")
    boolean getIsReverse();
    @Option(shortName ="n",defaultValue = "10",longName = "number")
    int  getNumber();
    @Unparsed
    String getFileName();

}
