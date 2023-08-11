import socket
import threading
from tkinter import *
from tkinter.filedialog import askopenfile
from tkVideoPlayer import TkinterVideo

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

sock.connect(("127.0.0.1", 8080))

data = sock.recv(1024).decode()


window = Tk()
window.title("HOMEFLIX")
window.geometry("700x450")
window.configure(bg="orange red")

file = data


if file is not None:
    global filename
    extension = ".mp4"
    index = file.index(extension)


    filename = file[:index+len(extension)]

    global videoplayer
    videoplayer = TkinterVideo(master=window, scaled=True)
    videoplayer.load(r"{}".format(filename))
    videoplayer.pack(expand=True, fill="both")

    videoplayer.play()

print(filename)
sock.close();
print("Hola")

window.mainloop()


while True:
    print("Holsdasd")
    sock.connect(("127.0.0.1", 8080))
    msg = sock.recv(1024).decode()
    print(msg)
    data = msg.replace("\r\n", "")
    if data == "play":
        videoplayer.play()
    elif data == "pause":
        videoplayer.pause()
    elif data == "restart":
        videoplayer.stop()
        videoplayer.play()
    else:
        sock.close()



