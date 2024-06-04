package org.example;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Sidebar {
  private Integer id;
  private String name;
  private String path;
  private String license;
  private String licenseHref;
  private String projectName;
  private String importSyntax;
  private List<Icon> icons;
}
