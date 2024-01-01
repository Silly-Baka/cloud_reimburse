package core;

import core.common.Result;

public interface IDGen {
    Result get(String key);
    boolean init();
}
