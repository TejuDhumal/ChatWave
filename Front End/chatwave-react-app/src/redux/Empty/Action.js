export const setFieldToNull = (type) => {
  return (dispatch) => {
    // Dispatch a regular action to update the field to null
    dispatch({ type: type, payload: null });
  };
};
