-- 一旦删除patient_infos条目就删除cart_patient_infos相关内容
CREATE OR REPLACE FUNCTION after_delete_patient_infoid_trigger()
  RETURNS TRIGGER AS $$
BEGIN
  DELETE FROM cart_patient_infos WHERE cart_patient_infos.patient_infoid = OLD.patient_infoid;
  RETURN NULL;
END;
$$
  LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS after_delete_patient_infoid_trigger ON patient_infos;
CREATE TRIGGER after_delete_patient_infoid_trigger
  AFTER DELETE ON patient_infos
FOR EACH ROW EXECUTE PROCEDURE after_delete_patient_infoid_trigger();

-- 一旦删除cart_patient_infos条目就删除或减少cart_items相关内容
CREATE OR REPLACE FUNCTION after_delete_cart_patient_info_trigger()
  RETURNS TRIGGER AS $$
BEGIN
  UPDATE cart_items SET quantity = quantity - 1 WHERE cart_items.cart_itemid = OLD.cart_itemid;
  DELETE FROM cart_items WHERE quantity = 0;
  RETURN NULL;
END;
$$
LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS after_delete_cart_patient_info_trigger ON cart_patient_infos;
CREATE TRIGGER after_delete_cart_patient_info_trigger
AFTER DELETE ON cart_patient_infos
FOR EACH ROW EXECUTE PROCEDURE after_delete_cart_patient_info_trigger();
