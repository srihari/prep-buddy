package org.apache.prepbuddy.preprocessor;


import org.apache.commons.lang.StringUtils;

public enum FileType {
    CSV(",") {
        @Override
        public String[] parseRecord(String record) {
            return record.split(",");
        }

        @Override
        public String join(String[] record) {
            return StringUtils.join(record,",");
        }
    },

    TSV("\t") {
        @Override
        public String[] parseRecord(String record) {
            return record.split("\t");
        }

        @Override
        public String join(String[] record) {
            return StringUtils.join(record,"\t");
        }
    };

    final private String delimiter;

    FileType(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public abstract String[] parseRecord(String record);

    public abstract String join(String[] record);
}
