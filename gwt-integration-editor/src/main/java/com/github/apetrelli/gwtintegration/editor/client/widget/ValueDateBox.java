/*
 * Copyright 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.github.apetrelli.gwtintegration.editor.client.widget;


import java.text.ParseException;
import java.util.Date;

import com.github.apetrelli.gwtintegration.editor.client.IsParseableEditor;
import com.github.apetrelli.gwtintegration.editor.client.ParseableValueEditor;
import com.github.apetrelli.gwtintegration.editor.client.TakesParseableValue;
import com.github.apetrelli.gwtintegration.editor.shared.ParseWithCauseException;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;

public class ValueDateBox extends DateBox implements TakesParseableValue<Date>, IsParseableEditor<ValueDateBox, Date> {
    private static final DefaultParsingFormat DEFAULT_FORMAT = GWT
            .create(DefaultParsingFormat.class);

    private ParsingFormat format;

    private ParseableValueEditor<ValueDateBox, Date> editor;

    public ValueDateBox() {
        this(new DatePicker(), null, DEFAULT_FORMAT);
    }

    public ValueDateBox(DatePicker picker, Date date, ParsingFormat format) {
        super(picker, date, format);
        this.format = format;
    }

    @Override
    public Date getValueOrThrow() throws ParseException {
        String text = getTextBox().getText().trim();
        return format.parseOrThrow(text);
    }

    public void setFormat(Format format) {
        super.setFormat(format);
        this.format = (ParsingFormat) format;
    }

    @Override
    public String getUnparsedText() {
        return getTextBox().getText().trim();
    }

    @Override
    public ParseableValueEditor<ValueDateBox, Date> asEditor() {
        if (editor == null) {
            editor = ParseableValueEditor.of(this);
        }
        return editor;
    }

    @Override
    public ParseableValueEditor<ValueDateBox, Date> asParseableEditor() {
        return asEditor();
    }

    public interface ParsingFormat extends DateBox.Format {

        public Date parseOrThrow(String text) throws ParseException;
    }

    public static class DefaultParsingFormat extends DateBox.DefaultFormat implements
            ParsingFormat {

        public DefaultParsingFormat() {
        }

        /**
         * @param arg0
         */
        public DefaultParsingFormat(DateTimeFormat format) {
            super(format);
        }

        @Override
        public Date parseOrThrow(String dateText) throws ParseException {
            Date date = null;
            try {
                if (dateText.length() > 0) {
                    date = getDateTimeFormat().parseStrict(dateText);
                }
            } catch (IllegalArgumentException exception) {
                throw new ParseWithCauseException(dateText, 0, exception);
            }
            return date;
        }

    }
}
