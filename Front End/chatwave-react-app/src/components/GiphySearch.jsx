import React, { useState } from "react";
import axios from "axios";
import { Modal, Box, TextField, Grid, Button } from "@mui/material";

const GiphySearch = ({ open, handleClose, handleSelectGif }) => {
  const [query, setQuery] = useState("");
  const [gifs, setGifs] = useState([]);

  const searchGifs = async () => {
    const response = await axios.get(
      `https://api.giphy.com/v1/gifs/search?api_key=mw39jTO2csrTCVRvPCB506F4cQobJ4GP&q=${query}&limit=20`
    );
    setGifs(response.data.data);
  };

  return (
    <Modal open={open} onClose={handleClose}>
      <Box
        sx={{
          position: "absolute",
          top: "50%",
          left: "50%",
          transform: "translate(-50%, -50%)",
          width: 400,
          maxHeight: "70vh",
          bgcolor: "background.paper",
          boxShadow: 24,
          p: 4,
          overflowY: "auto",
          borderRadius: "16px",
        }}
      >
        <TextField
          fullWidth
          variant="outlined"
          placeholder="Search Giphy..."
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          onKeyPress={(e) => {
            if (e.key === "Enter") {
              searchGifs();
            }
          }}
        />
        <Button
          fullWidth
          variant="contained"
          onClick={searchGifs}
          sx={{ mt: 2, bgcolor: "#1271ff" }}
        >
          Search
        </Button>
        <Grid container spacing={2} sx={{ mt: 2 }}>
          {gifs.map((gif) => (
            <Grid item xs={6} key={gif.id}>
              <img
                src={gif.images.fixed_width.url}
                alt={gif.title}
                style={{ cursor: "pointer", width: "100%" }}
                onClick={() => handleSelectGif(gif.images.fixed_width.url)}
              />
            </Grid>
          ))}
        </Grid>
      </Box>
    </Modal>
  );
};

export default GiphySearch;
