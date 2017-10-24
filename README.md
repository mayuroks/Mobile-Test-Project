# Gallery with Infinite Scroll
The core purpose of this exercise is to build **Gallery with Infinite Scroll** Android app. To add more constraints, I need to integrate an image providing API such as Imgur, images should be large, scrolling should be smooth and they should not consume much memory.

# Breaking Into Sub-problems
 - To implement infinite scrolling. In the app I need to add a scrollListener. Here, I will calculate that end of the gallery has reached and will fire the API to get more images.
 - Getting large images from Imgur via API.
 
# First Attempt
Implementing scrollListener, which fires API once end of the list has reached,  was fairly complex. I thought the hardest part was done. Now I just need to get images from Imgur API and display them. That was not the case :D.

Using this API,
```
https://api.imgur.com/3/gallery/search/top/top/1?q=cats&q_size_px=big
```
I downloaded some large images.

```javascript
{  
   "data":[  
      {  
         "id":"lXk9qty",
         "type":"image/jpeg",
         "animated":false,
         "width":3120,
         "height":4208,
         "size":819435,     // <===== Size ~ 810KB
         "link":"https://i.imgur.com/lXk9qty.jpg"
      },
      {  
         "id":"0tmWCV2",
         "type":"image/jpeg",
         "width":2937,
         "height":2203,
         "size":631905,     // <===== Size ~ 630KB
         "link":"https://i.imgur.com/0tmWCV2.jpg"
      }
   ]
}
```

# Large Images and Mobile Data
The size of individual image seems to be small enough. But since we are implementing infinite scroll, this problem escalates in a matter of seconds (or scrolls).

Lets say, on an average, each image  is 500KB in size. If I scroll past 100 images, then data  downloaded from the network would be 
**500KB x 100 = 50MB**

That's a **Lot-of-Data** to download while ... scrolling. Once downloaded, the images will be loaded in app memory and would consume 50MB or more. 

This will make the app sluggish and it will go OOM(out-of-memory) pretty quickly.

# How does Facebook or Instagram do it?
Let's take Instagram and Facebook for example. They load user images and albums with lots of images. Yet these apps don't consume 50MB data for a few scrolls or feel sluggish or go OOM and crash.

After reading this article,
[Improving Facebook on Android](https://code.facebook.com/posts/485459238254631/improving-facebook-on-android/)
> Instead of loading a full image, the device only loads the image it needs to show (such as a thumbnail, preview, or full image at lower resolution).

**it started to make sense to load thumbnails rather than large images.**

Even the Instagram API,
```
https://api.instagram.com/v1/tags/{tag-name}/media/recent?access_token=ACCESS-TOKEN
```
Gives the options
- low_resolution(306 x 306), 
- thumbnail(150 x 150) and 
- standard_resolution(612 x 612).

```javascript
"images": {
            "low_resolution": {
                "url": "http://distillery.s3.amazonaws.com/media/2011/02/02/f9443f3443484c40b4792fa7c76214d5_6.jpg",
                "width": 306,
                "height": 306
            },
            "thumbnail": {
                "url": "http://distillery.s3.amazonaws.com/media/2011/02/02/f9443f3443484c40b4792fa7c76214d5_5.jpg",
                "width": 150,
                "height": 150
            },
            "standard_resolution": {
                "url": "http://distillery.s3.amazonaws.com/media/2011/02/02/f9443f3443484c40b4792fa7c76214d5_7.jpg",
                "width": 612,
                "height": 612
            }
        }
```

Though the size is not mentioned, I am pretty sure each of these thumbnails consume around 10KB(I will prove that at the end :D)

# Imgur API Problems
The biggest problem wtih Imgur APIs is lack of thumbnails. 

To tackle this, I switched from big images to small images. To get small image, we need to pass a parameter q_size_px=small

Using this API,
```
https://api.imgur.com/3/gallery/search/top/top/1?q=cats&q_size_px=small
```
These are the images I got from the response. As you can see, 
```
https://i.imgur.com/6gBQrTJ.jpg
image/jpeg
693 x 305
 === Size ~ 34KB    

https://i.imgur.com/HpzitC8.jpg
image/jpeg
537 x 499
 === Size ~  85KB

https://i.imgur.com/6nHHU6N.jpg
image/jpeg
596 x 600
 === Size ~  25KB

https://i.imgur.com/tGmhqOZ.jpg
image/jpeg
600 x 600
 === Size ~  183KB
```

These small images don't have any standard dimensions or a fixed file size. They don't look optimized for a gallery android app.

# Efficiently Loading Inefficient Images
The library that I used to load images is Fresco. This is an image loading library open-sourced by Facebook.

The problem of **Gallery with Infinite Scroll** is actually a production problem for Facebook and Instagram. This one of the reasons why [Facebook open-sourced Fresco](https://code.facebook.com/posts/366199913563917/introducing-fresco-a-new-image-library-for-android/).

I can't optimize the images from Imgur APIs. But I can display these inefficient images efficiently and give a smooth scrolling effect to the user.

 Here are few Android optimizations that I did:
- Instead reaching the end and firing API to load images, I fire the API few elements before it happens.
- Downloaded images are cropped and resized 300x300. This gives them a square-thumbnail look.
- Show a progressBar for each image, to indicate that the image is being downloaded.
- I added rounded corners to the thumbnails, which makes them look a bit cooler.

After doing these things, there was a huge improvement in the UX. The app did not consume much memory and it feels quite smooth.

Still, the app was far from being a perfect solution as compared to Instagram or Facebook.

# Experimenting with Python and Image Resizing
Rather than optimizing the Android app, I decided to build a server side thumbnail cache.

I download 100 images from Imgur.  Using Pillow(python image librar), I generated 300 x 300 thumbnails. I didn't do any quality optimizations on those images. 

**Each of these thumbnails is merely 5KB in size.** I then, hosted all those thumbnails on my Digital Ocean server and served it via Nginx.

With that said, I just changed 4 lines of app code to download images from my server.

**The result was insane.** This time images loaded very fast. This clearly shows lesser KBs to download, faster the images are shown to the user.

# In Conclusion
Rather than trying to display large images in a gallery, its better to show thumbnails which can be loaded quickly.

On server-side, a thumbnail-cache is very much needed. This includes low-res / med-res / preview image-cache so that the network doesn't become a bottleneck. Otherwise, there's not much the Android app can do.

For Android side, use a good library like Fresco which will allow you to cache the images on disk. So that, the next time when the same image is requested, it is loaded directly from the disk.
