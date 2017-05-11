CREATE OR REPLACE FUNCTION GET_UUID RETURN VARCHAR IS
  GUID VARCHAR(50);
BEGIN
  GUID := LOWER(RAWTOHEX(SYS_GUID()));
  RETURN SUBSTR(GUID, 1, 8) || '-' || SUBSTR(GUID, 9, 4) || '-' || SUBSTR(GUID,
                                                                          13,
                                                                          4) || '-' || SUBSTR(GUID,
                                                                                              17,
                                                                                              4) || '-' || SUBSTR(GUID,
                                                                                                                  21,
                                                                                                                  12);
END GET_UUID;
