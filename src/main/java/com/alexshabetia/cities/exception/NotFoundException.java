package com.alexshabetia.cities.exception;

public class NotFoundException extends RuntimeException {
  public NotFoundException() {
    super("Resource with given UUID is not found");
  }
}
