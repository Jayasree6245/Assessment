import { useState } from "react";
import "./App.css";
function App() {
    const [content,setContent]=useState("");
    const [expires, setExpires]=useState("");
    const [views, setViews]=useState("");
    const [link, setLink]=useState("");
    const createPaste=async()=> {
        const res=await fetch("http://localhost:8080/paste",{
            method:"POST",
            headers:{'Content-Type':"application/json"},
            body:JSON.stringify({
                content,
                expiresInMinutes:expires,
                maxViews: views
               })
});
const data=await res.json();
setLink(data.link);
    };
    return (
        <div className="container">
            <h2>Pastebin</h2>
            <textarea
            placeholder="Enter text here"
            value={content}
            onChange={(e)=>setContent(e.target.value)}
            />
            <input placeholder="Expire in minutes"
            onChange={(e)=>setExpires(e.target.value)}/>
            <input placeholder="Max views"
            onChange={(e)=> setViews(e.target.value)}/>
            <button onClick={createPaste}>Create Paste</button>
            {link && <p>Shareable Link: <a href={link}>{link}</a></p>}
        </div>
    );
}
export default App;