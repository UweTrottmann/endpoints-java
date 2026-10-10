/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.api.server.spi;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.mock.web.MockHttpServletRequest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletInputStream;

/**
 * Tests for {@link IoUtil}.
 */
@RunWith(JUnit4.class)
public class IoUtilTest {

  @Test
  public void testReadStreamEmptyInput() throws Exception {
    testReadStream("");
  }

  @Test
  public void testReadStream() throws Exception {
    testReadStream("abc123");
  }

  @Test
  public void testReadStream_largeInput() throws Exception {
    testReadStream("abc123".repeat(10_000));
  }

  @Test
  public void testReadStream_readReturnsZero() throws Exception {
    // Stream that returns 0 bytes before the data and between chunks. Extends InputStream and not
    // ByteArrayInputStream, as the latter overrides readAllBytes and would not call read.
    final InputStream content = new ByteArrayInputStream("abc123".getBytes(StandardCharsets.UTF_8));
    InputStream is = new InputStream() {
      private int calls = 0;

      @Override
      public int read() throws IOException {
        return content.read();
      }

      @Override
      public int read(byte[] b, int off, int len) throws IOException {
        if (calls++ % 2 == 0) {
          return 0;
        }
        return content.read(b, off, Math.min(len, 3));
      }
    };
    assertEquals("abc123", IoUtil.readStream(is));
  }

  @Test
  public void testGetRequestInputStream() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setContent(compress("test".getBytes(StandardCharsets.UTF_8)));
    request.addHeader("Content-Encoding", "gzip");
    InputStream stream = IoUtil.getRequestInputStream(request);
    assertThat(stream).isInstanceOf(GZIPInputStream.class);
    assertThat(IoUtil.readStream(stream)).isEqualTo("test");
  }

  @Test
  public void testGetRequestInputStream_headerSplitAcrossReads() throws Exception {
    // Request stream that returns 0 or 1 bytes per read
    final InputStream content =
        new ByteArrayInputStream(compress("test".getBytes(StandardCharsets.UTF_8)));
    MockHttpServletRequest request = new MockHttpServletRequest() {
      @Override
      public ServletInputStream getInputStream() {
        return new ServletInputStream() {
          private int calls = 0;

          @Override
          public int read() throws IOException {
            return content.read();
          }

          @Override
          public int read(byte[] b, int off, int len) throws IOException {
            if (calls++ % 2 == 0) {
              return 0;
            }
            return content.read(b, off, Math.min(len, 1));
          }
        };
      }
    };
    request.addHeader("Content-Encoding", "gzip");
    InputStream stream = IoUtil.getRequestInputStream(request);
    assertThat(stream).isInstanceOf(GZIPInputStream.class);
    assertThat(IoUtil.readStream(stream)).isEqualTo("test");
  }

  @Test
  public void testGetRequestInputStream_emptyStream() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setContent(new byte[0]);
    request.addHeader("Content-Encoding", "gzip");
    InputStream stream = IoUtil.getRequestInputStream(request);
    assertThat(stream).isNotInstanceOf(GZIPInputStream.class);
    assertThat(IoUtil.readStream(stream)).isEqualTo("");
  }

  @Test
  public void testGetRequestInputStream_headerWithPlaintext() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setContent("test".getBytes(StandardCharsets.UTF_8));
    request.addHeader("Content-Encoding", "gzip");
    InputStream stream = IoUtil.getRequestInputStream(request);
    assertThat(stream).isNotInstanceOf(GZIPInputStream.class);
    assertThat(IoUtil.readStream(stream)).isEqualTo("test");
  }

  @Test
  public void testReadRequestBody() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setContent(compress("test".getBytes(StandardCharsets.UTF_8)));
    request.addHeader("Content-Encoding", "gzip");
    assertThat(IoUtil.readRequestBody(request)).isEqualTo("test");
  }

  private void testReadStream(String input) throws Exception {
    InputStream is = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
    assertEquals(input, IoUtil.readStream(is));
  }

  private static byte[] compress(byte[] bytes) {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      GZIPOutputStream gos = new GZIPOutputStream(baos);
      gos.write(bytes, 0, bytes.length);
      gos.close();
      return baos.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
