import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import Login from "./Login.js"
import Chat from "./Chat.js"
import Register from "./Register.js"


import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Login />
  },
  {
    path: "/Chat",
    element: <Chat />
  },
  {
    path: "/Register",
    element: <Register />
  }
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);

