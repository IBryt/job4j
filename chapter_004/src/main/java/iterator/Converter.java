package iterator;

import java.util.Iterator;

public class Converter {
    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        return new Iterator<Integer>() {
            private Iterator external = it;
            private Iterator internal;

            @Override
            public boolean hasNext() {
                if ((this.internal == null) || (!this.internal.hasNext() && this.external.hasNext())) {
                   this.internal = (Iterator) this.external.next();
                }
                return this.internal.hasNext();
            }

            @Override
            public Integer next() {
                if (this.internal == null || !this.internal.hasNext()) {
                    this.internal = (Iterator) this.external.next();
                }
                return (Integer) this.internal.next();
            }
        };
    }
}
