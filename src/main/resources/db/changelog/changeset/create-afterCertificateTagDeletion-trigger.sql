-- changeset nekear:create-afterCertificateTagDeletion-trigger
CREATE TRIGGER AfterCertificateTagDeletion
    AFTER DELETE ON certificates_to_tags
    FOR EACH ROW
BEGIN
    DECLARE tag_count INT;
    SELECT COUNT(*) INTO tag_count FROM certificates_to_tags WHERE tag_id = OLD.tag_id;
    IF tag_count = 0 THEN
        DELETE FROM tags WHERE id = OLD.tag_id;
    END IF;
END;