-- changeset nekear:create-countConnectedTagsWithName-function
CREATE FUNCTION countConnectedTagsWithName(c_id INT, tag_name VARCHAR(255))
    RETURNS INT
    DETERMINISTIC
BEGIN
    DECLARE tags_count INT;
    SELECT COUNT(*) INTO tags_count
    FROM certificates_to_tags
             JOIN tags ON certificates_to_tags.tag_id = tags.id
    WHERE certificates_to_tags.certificate_id = c_id
    AND tags.name LIKE tag_name;

    RETURN tags_count;
END;