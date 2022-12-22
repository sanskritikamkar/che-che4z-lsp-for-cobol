/*
 * Copyright (c) 2022 Broadcom.
 * The term "Broadcom" refers to Broadcom Inc. and/or its subsidiaries.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Broadcom, Inc. - initial API and implementation
 *
 */
package org.eclipse.lsp.cobol.core.preprocessor.delegates.injector.providers;

import com.google.common.collect.ImmutableList;
import org.eclipse.lsp.cobol.common.copybook.*;
import org.eclipse.lsp.cobol.common.file.FileSystemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * This unit tests check the {@link FileCopybookContentProvider} class functionality
 */
@ExtendWith(MockitoExtension.class)
class FileCopybookContentProviderTest {
  private FileCopybookContentProvider contentProvider;
  private CopybookConfig copybookConfig;
  @Mock private FileSystemService files;

  @BeforeEach
  void setupMocks() {
    copybookConfig = new CopybookConfig(CopybookProcessingMode.ENABLED, SQLBackend.DB2_SERVER, ImmutableList.of());
    contentProvider = new FileCopybookContentProvider(files);
  }

  @Test
  void testReadReturnsContent() {
    when(files.readImplicitCode(any())).thenReturn("content");
    Optional<CopybookModel> model = contentProvider.read(copybookConfig, new CopybookName("copybook"), "uri", "uri");
    assertEquals("content", model.get().getContent());
  }

  @Test
  void testReadFailed() {
    when(files.readImplicitCode(any())).thenReturn(null);
    Optional<CopybookModel> model = contentProvider.read(copybookConfig, new CopybookName("copybook"), "uri", "uri");
    assertFalse(model.isPresent());
  }
}