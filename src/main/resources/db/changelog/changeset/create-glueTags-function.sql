-- changeset nekear:create-glueTags-function
CREATE FUNCTION glueTags(c_id INT)
    RETURNS VARCHAR(1000)
    DETERMINISTIC
BEGIN
    DECLARE result VARCHAR(1000);
    SELECT GROUP_CONCAT(CONCAT_WS('#', tags.id, tags.name) SEPARATOR '?')
    INTO result
    FROM certificates_to_tags
             JOIN tags ON certificates_to_tags.tag_id = tags.id
    WHERE certificates_to_tags.certificate_id = c_id;

    RETURN result;
END;
