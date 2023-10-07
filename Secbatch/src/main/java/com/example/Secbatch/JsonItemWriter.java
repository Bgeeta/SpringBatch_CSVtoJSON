package com.example.Secbatch;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;

import org.springframework.core.io.FileSystemResource;

import org.springframework.context.annotation.Scope;

@Scope("step")
public class JsonItemWriter<T> extends FlatFileItemWriter<T> {

    public JsonItemWriter(String outputFilePath) {
        // set the output file
        setResource(new FileSystemResource(outputFilePath));

        // Configure the line aggregator to serialize objects as JSON
        JsonLineAggregator<T> lineAggregator = new JsonLineAggregator<>();
        setLineAggregator(lineAggregator);

        // Configure the FooterCallback sot write a ']' at the end of the file.
        setFooterCallback(new FlatFileFooterCallback() {

            @Override
            public void writeFooter(Writer writer) throws IOException {
                writer.write("]");
                lineAggregator.setIsFirst(true);
            }
        });

        
    }

}
