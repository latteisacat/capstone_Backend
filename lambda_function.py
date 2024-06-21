import os
import boto3
import subprocess
from PIL import Image
from io import BytesIO
from moviepy.editor import VideoFileClip

s3_client = boto3.client('s3')

def lambda_handler(event, context):
    bucket = event['Records'][0]['s3']['bucket']['name']
    key = event['Records'][0]['s3']['object']['key']
    if key.endswith(("mp4", "avi", "mov", "wmv", "flv", "mkv", "webm")):
        print("This is a video!")
        generate_video_thumbnail(bucket, key)
    elif key.endswith(("jpg", "jpeg", "png", "gif", "bmp", "tiff")):
        print("This is an image!")
        generate_image_thumbnail(bucket, key)
    else:
        print(f"Unsupported file type for key: {key}")
    
def generate_image_thumbnail(bucket, key):
    download_path = f'/tmp/{os.path.basename(key)}'
    upload_path = f'thumbnails/{os.path.basename(key)}.thumbnail'

    # Ensure the directory exists (not necessary for /tmp but kept for consistency)
    os.makedirs(os.path.dirname(download_path), exist_ok=True)
    
    s3_client.download_file(bucket, key, download_path)
    
    with Image.open(download_path) as image:
        image.thumbnail((400, 400))
        if image.mode in ("RGBA", "LA") or (image.mode == "P" and "transparency" in image.info):
            # Convert RGBA to RGB
            background = Image.new("RGB", image.size, (255, 255, 255))
            background.paste(image, mask=image.split()[3] if image.mode == 'RGBA' else image.convert("RGBA").split()[3])
            image = background
        buffer = BytesIO()
        image.save(buffer, 'JPEG')
        buffer.seek(0)
        
        s3_client.put_object(Bucket=bucket, Key=upload_path, Body=buffer, ContentType='image/jpeg')
    
    os.remove(download_path)

def generate_video_thumbnail(bucket, key):
    download_path = f'/tmp/{os.path.basename(key)}'
    upload_path = f'thumbnails/{os.path.basename(key)}.thumbnail'

    
    print(f"Downloading file: {key} from bucket: {bucket}")
    s3_client.download_file(bucket, key, download_path)
    
    clip = VideoFileClip(download_path)
    frame = clip.get_frame(0.1)  # get video frame at specific time (in seconds)
    
    # Convert the numpy array to an image
    image = Image.fromarray(frame)
    image.thumbnail((400, 400))
    
    # Save the image to a BytesIO object
    buffer = BytesIO()
    image.save(buffer, format='JPEG')
    buffer.seek(0)
    

    s3_client.put_object(Bucket=bucket, Key=upload_path, Body=buffer, ContentType='image/jpeg')
    
    os.remove(download_path)
    
