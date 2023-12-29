package src.core;

import src.core.common.Result;

public interface IDGen {
    Result get(String key);
    boolean init();
}
